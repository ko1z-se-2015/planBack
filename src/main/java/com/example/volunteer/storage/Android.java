package com.example.volunteer.storage;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Android {
    private int num;

    public Android(int num) {
        this.num = num;
    }
}
