package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Task;

public interface TaskService {

    List<Task> findAll();              // Récupérer toutes les tâches

    Task save(Task task);              // Créer une nouvelle tâche

    void deleteById(Long id);          // Supprimer une tâche par ID

    Task update(Long id, Task task);   // Mettre à jour une tâche par ID
}