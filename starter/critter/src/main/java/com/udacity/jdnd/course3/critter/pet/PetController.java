package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.ConverterUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;
    private final ConverterUtil converter;

    public PetController(PetService petService, ConverterUtil converter) {
        this.petService = petService;
        this.converter = converter;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = petService.addPet(converter.convertPetDTOtoEntity(petDTO));
        return converter.convertEntityToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return converter.convertEntityToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return converter.convertEntitiesToPetDTOList(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        return converter.convertEntitiesToPetDTOList(pets);
    }
}
