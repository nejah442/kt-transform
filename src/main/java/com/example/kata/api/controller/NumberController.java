package com.example.kata.api.controller;

import com.example.kata.api.service.TransformationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@RequestMapping("/api/kata")
@Validated
@Tag(name = "Number Transformation", description = "Transforme des nombres entre 0 et 100 selon les règles FOO, BAR, QUIX")
public class NumberController {
    private final TransformationService transformationService;

    public NumberController(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @Operation(summary = "Transforme un nombre", description = "Retourne la chaine correspondante aux règles FOO, BAR, QUIX")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succès : résultat de la transformation renvoyé (Exemple : FOOBARBAR)"),
            @ApiResponse(responseCode = "400", description = "Requête invalide : nombre manquant, hors limites, ou non numérique",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Nombre null",
                                            summary = "Paramètre absent",
                                            value = "Le paramètre 'number' est requis et ne peut être null"
                                    ),
                                    @ExampleObject(
                                            name = "Nombre invalide",
                                            summary = "Type incorrect",
                                            value = "Le paramètre 'number' doit être un entier valide"
                                    ),
                                    @ExampleObject(
                                            name = "Nombre hors limites",
                                            summary = "Valeur hors bornes",
                                            value = "Le nombre doit être compris entre 0 et 100"
                                    )
                            }
                    ))})
    @GetMapping("/transform")
    public ResponseEntity<String> getResult(@RequestParam
                                            @Parameter(
                                                    name = "number",
                                                    description = "Nombre entier entre 0 et 100 à transformer",
                                                    example = "15",
                                                    required = true
                                            )
                                            Integer number) {

        if (number < 0 || number > 100) {
            throw new IllegalArgumentException("Le nombre doit être compris entre 0 et 100");
        }

        String result = transformationService.transform(number);

        return ResponseEntity.ok(result);
    }

}
