package com.example.TwelveChannel.Thread;

import java.sql.Timestamp;

public record ThreadEntity(int id, int authar, String threadtitle, String comment,
                           String image_name, String image_base64, Timestamp created_at, Timestamp updated_at, int view_count) {
}