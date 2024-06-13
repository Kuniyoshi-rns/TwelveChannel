package com.example.TwelveChannel.Comment;

public record CommentEntity(int id,
                            int thread_id,
                            int user_id,
                            String comment,
                            String image_name,
                            String image_base64,
                            String created_at) {}
