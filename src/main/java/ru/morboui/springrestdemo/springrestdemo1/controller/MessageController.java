package ru.morboui.springrestdemo.springrestdemo1.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.morboui.springrestdemo.springrestdemo1.domain.Views;
import ru.morboui.springrestdemo.springrestdemo1.domain.Message;
import ru.morboui.springrestdemo.springrestdemo1.repo.MessageRepo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final MessageRepo messageRepo;

  @Autowired
  public MessageController(MessageRepo messageRepo) {
    this.messageRepo = messageRepo;
  }

  @GetMapping
  @JsonView(Views.IdName.class)
    public List<Message> list() {
      return messageRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView({Views.FullMessage.class})
    public Message getOne(@PathVariable("id") Message message) {
      return message;
    }

  @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepo.save(message);

    }

    @PutMapping("{id}")
    public Message update(
        @PathVariable("id") Message messageFromDb,
        @RequestBody Message message
    ) {
      BeanUtils.copyProperties(message, messageFromDb, "id");
      return messageRepo.save(messageFromDb);

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable ("id") Message message) {
        messageRepo.delete(message);
    }
}
