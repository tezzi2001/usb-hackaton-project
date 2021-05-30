package com.heroku.labshare.service.async;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.heroku.labshare.model.AdvancedSearchMap;
import com.heroku.labshare.model.Task;
import com.heroku.labshare.repository.AdvancedSearchMapRepository;
import com.heroku.labshare.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsyncFileCrawlerService {

    @Value("classpath:static/empty.pdf")
    Resource resourceFile;

    private static final String PDF_FORMAT = ".pdf";

    private final OfficeParser officeParser;
    private final PDFParser pdfParser;
    private final TXTParser txtParser;
    private final TaskRepository taskRepository;
    private final AdvancedSearchMapRepository advancedSearchMapRepository;
    private final DbxClientV2 dbxClient;

    @PostConstruct
    public void crawlerRun() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(this::crawlerTask, 0, 12, TimeUnit.HOURS);
    }

    public void crawlerForcedRun() {
        new Thread(this::crawlerTask).start();
    }

    private void crawlerTask() {
        List<Task> tasks = taskRepository.findAll();
        tasks.forEach(task -> {
            try {
                File file = resourceFile.getFile();
                OutputStream outputStream = new FileOutputStream(file);
                FileMetadata fileMetadata = dbxClient.files().download(task.getFilePath()).download(outputStream);
                if (fileMetadata.getName().endsWith(PDF_FORMAT)) {
                    List<String> words = parsePDF(file);
                    save(words, task);
                }
            } catch (DbxException | IOException | TikaException | SAXException e) {
                e.printStackTrace();
            }
        });
    }

    private List<String> parsePDF(File file) throws IOException, TikaException, SAXException {
        ContentHandler contenthandler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        pdfParser.parse(new FileInputStream(file), contenthandler, metadata, new ParseContext());
        return Arrays.stream(contenthandler.toString().split("\\s+"))
                .distinct()
                .collect(Collectors.toList());
    }

    private void save(List<String> words, Task task) {
        advancedSearchMapRepository.deleteAll();
        words.forEach(word -> {
            AdvancedSearchMap advancedSearchMap = new AdvancedSearchMap();
            advancedSearchMap.setTask(task);
            advancedSearchMap.setWord(word);
            advancedSearchMapRepository.save(advancedSearchMap);
        });
    }
}
