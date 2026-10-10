package com.journal.journalapp.service;

import com.journal.journalapp.entity.user;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class userargumentsprovider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext)throws Exception{
        return Stream.of(
                Arguments.of(user.builder().username("shyam").password("shyam").build()),
                Arguments.of(user.builder().username("suraj").password("suraj").build())
        );
    }
}
