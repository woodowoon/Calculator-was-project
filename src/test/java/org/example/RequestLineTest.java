package org.example;

import org.example.RequestLine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    void create() {
        RequestLine requestLine = new RequestLine("GET /calculate?operand1=11&operatoe=*&operand2=55 HTTP/1.1");

        assertThat(requestLine).isNotNull();
        assertThat(requestLine).isEqualTo(new RequestLine("GET", "/calculate", "operand1=11&operatoe=*&operand2=55"));
    }
}
