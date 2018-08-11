package com.danielcs.mercurychat.services;

import com.danielcs.webserver.socket.annotations.HttpRequestAssembler;

import java.util.Set;

@HttpRequestAssembler
public interface HttpRequest {
    Set<String> getJsonWithBearer(String url, String token);
}
