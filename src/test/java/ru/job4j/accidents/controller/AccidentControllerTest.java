package ru.job4j.accidents.controller;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.*;
import ru.job4j.accidents.Main;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@Transactional
class AccidentControllerTest {

    @MockBean
    private AccidentService accidentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void shouldReturnAllAccidents() throws Exception {
        this.mockMvc.perform(get("/accidents/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/all"));
    }

    @Disabled
    @Test
    @WithMockUser
    public void shouldReturnNewAccident() throws Exception {
        this.mockMvc.perform(post("/accidents/saveAccident")
                        .param("id", "0")
                        .param("name", "Name1")
                        .param("text", "Text1")
                        .param("address", "Address1")
                        .param("type.id", "1")
                        .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).save(argument.capture());
        assertThat(argument.getValue().getName()).isEqualTo("Name1");
    }
}