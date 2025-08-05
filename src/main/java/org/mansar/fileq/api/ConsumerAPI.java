package org.mansar.fileq.api;

import lombok.RequiredArgsConstructor;
import org.mansar.fileq.dto.PullResponse;
import org.mansar.fileq.service.ITopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consumer")
public class ConsumerAPI {

    private final ITopicService topicService;

    @GetMapping("/pull")
    public PullResponse pullNext(@RequestParam String topic) {
        return topicService.pullNext(topic);
    }
}
