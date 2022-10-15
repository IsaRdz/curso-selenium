Feature: Test de apis login de usuarios

  Scenario Outline: Post login user
    Given Tengo un json con datos para el logueo de cuenta "<email>" y "<password>"
    When Hago la peticion POST para loguear un usuario al endpoint /login
    Then Espero que la respuesta del servicio contenga codigo de estado 200
    And Espero que el cuerpo de la respuesta contenga el token no nulo

    Examples: datos de pruebas
    |email             |password  |
    |eve.holt@reqres.in|cityslicka|