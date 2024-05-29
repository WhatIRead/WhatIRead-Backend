package com.mvp1.whatiread.client;

import com.mvp1.whatiread.model.OpenLibResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "open-library-client", url = "https://openlibrary.org")
public interface OpenLibraryClient {

  @GetMapping(path = "search.json")
  OpenLibResponse searchBooksByTitle(@RequestParam String title,
      @RequestParam(name = "fields") String fields,
      @RequestParam(name = "limit") int limit);
}