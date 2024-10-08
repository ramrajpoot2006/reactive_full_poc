package com.accenture.next.customerapi.util;

import static com.accenture.next.customerapi.constant.ErrorConstants.API_MESSAGE_KEY;
import static com.accenture.next.customerapi.constant.ErrorConstants.DOT_STRING;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

  private final MessageSource messageSource;

  public MessageHelper(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String buildMessage(String code, String... args) {
    if (args.length > 0) {
      return messageSource.getMessage(String.join("", API_MESSAGE_KEY, DOT_STRING, code), args, Locale.US);
    } else {
      return messageSource.getMessage(String.join("", API_MESSAGE_KEY, DOT_STRING, code), null, Locale.US);
    }
  }

}
