package com.example.volunteer.modules;

import com.example.volunteer.entities.SocialWork;
import lombok.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SocialWorksToDelete {
    private List<SocialWork> items;
}
