package com.example.TwelveChannel.Thread;

import java.sql.Timestamp;

public record ThreadEntity(int id, int creator, String thread_title, String comment,
                           String image_name, String image_base64, String created_at, String updated_at, int view_count) {
}