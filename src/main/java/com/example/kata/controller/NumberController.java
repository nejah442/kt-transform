package com.example.kata.controller;

import com.example.kata.service.TransformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/number")
@Validated
@Tag(name = "Number Transformation", description = "Transforme des nombres entre 0 et 100 selon les règles FOO, BAR, QUIX")
public class NumberController {
    private final TransformationService transformationService;

    public NumberController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @Operation(summary = "Transforme un nombre", description = "Retourne la chaine correspondante aux règles FOO, BAR, QUIX")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Succès, retourne le résultat de la transformation"), @ApiResponse(responseCode = "400", description = "Entrée invalide : nombre hors limites (0-100) ou non numérique")})
    @GetMapping("/transform")
    public ResponseEntity<String> getResult(@RequestParam Integer number) {

        if (number < 0 || number > 100) {
            throw new IllegalArgumentException("Le nombre doit être compris entre 0 et 100");
        }

        String result = transformationService.transform(number);

        return ResponseEntity.ok(result);
    }

}
