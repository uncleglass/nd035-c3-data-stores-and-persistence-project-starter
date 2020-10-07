package com.udacity.jdnd.course3.critter.pet;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        if (pet.getOwner() != null) {
            pet.getOwner().getPets().add(savedPet);
        }
        return savedPet;
    }

    public Pet getPetById(long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("pet Id " + petId + " does not exist in the DB"));
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }
}

