package org.mansar.fileq.api;

import org.mansar.fileq.dto.FileResource;
import org.mansar.fileq.dto.PullResponse;
import org.mansar.fileq.dto.PushResponse;
import org.mansar.fileq.service.ITopicService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/consumer")
public class ConsumerAPI {

    private final ITopicService topicService;

    public ConsumerAPI(ITopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/pull")
    public PullResponse pullNext(@RequestParam(required = false) String topic) {
        return topicService.pullNext(topic);
    }

    @PostMapping("/upload")
    public PushResponse pushNext(@RequestParam("file") MultipartFile file,
                                 @RequestParam("topic") String topic) {
        return topicService.pushItem(topic, file);
    }
    @GetMapping("/download/{topicItemId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("topicItemId") String topicItemId) {
        FileResource fileResource = topicService.download(topicItemId);
        if (fileResource == null)
            return ResponseEntity.ofNullable(null);

       return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.fileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .body(fileResource.resource());
    }
}
