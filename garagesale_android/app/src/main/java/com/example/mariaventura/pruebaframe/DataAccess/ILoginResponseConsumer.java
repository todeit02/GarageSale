package com.example.mariaventura.pruebaframe.DataAccess;

/**
 * Created by Tobias on 18/04/2018.
 */

public interface ILoginResponseConsumer
{
    void consume(boolean areCredentialsValid, String username, String password);
}