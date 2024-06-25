package com.example.TwelveChannel.Thread;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ThreadAddForm {
    @NotBlank(message = "")
    @Length(min = 1,max = 50)
    String title;

    String comment;

    @NotBlank(message = "タグを１つ以上設定してください")
    String tag;

    String image_name;

    String image_base64;

}
