package com.solbeg.mapper;

import com.solbeg.dto.UserDTO;
import com.solbeg.model.User;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class UserMapperTest {

    @Inject
    private UserMapper userMapper;

    @Test
    void toEntity() {
        //Given
        UserDTO dto = new UserDTO(100L, "fake@email.com");
        //When
        User user = userMapper.toEntity(dto);
        //Then
        assertThat(dto).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    void toDTO() {
        //Given
        User user = new User();
        user.setId(100L);
        user.setEmail("fake@email.com");
        user.setPassword("123456");
        //When
        UserDTO userDTO = userMapper.toDTO(user);
        //then
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
    }
}