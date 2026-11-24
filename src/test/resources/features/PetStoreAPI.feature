@PetStore
Feature: API Petstore

  Como usuario de la API de Petstore
  Quiero gestionar mascotas mediante los endpoints de Pet
  Para verificar el correcto funcionamiento de las operaciones CRUD

  @CP01_Pet
  Scenario Outline: Crear una mascota exitosamente
    Given el actor establece el endpoint de petstore
    When el actor crea una mascota con id "<id>" nombre "<name>" categoria "<category>" y estado "<status>"
    Then el codigo de respuesta debe ser 200
    And el campo "id" de la respuesta debe ser "<id>"
    And el campo "name" de la respuesta debe ser "<name>"
    And el campo "category.name" de la respuesta debe ser "<category>"
    And el campo "status" de la respuesta debe ser "<status>"

    Examples:
      | id        | name  | category | status    |
      | 987654321 | Firulais | Perros   | available |

  @CP02_Pet
  Scenario: Consultar mascotas disponibles por estado
    Given el actor establece el endpoint de petstore
    When el actor consulta las mascotas con estado "available"
    Then el codigo de respuesta debe ser 200
    And la lista de mascotas no debe estar vacia
    And el campo "[0].status" de la respuesta debe ser "available"

  @CP03_Pet
  Scenario Outline: Actualizar los datos de una mascota exitosamente
    Given el actor establece el endpoint de petstore
    And el actor crea una mascota con id "987654322" nombre "Max" categoria "Perros" y estado "available"
    When el actor actualiza la mascota con nombre "<name>" categoria "<category>" y estado "<status>"
    Then el codigo de respuesta debe ser 200
    And el campo "id" de la respuesta debe coincidir con el id creado
    And el campo "name" de la respuesta debe ser "<name>"
    And el campo "status" de la respuesta debe ser "<status>"

    Examples:
      | name  | category | status |
      | Rocky | Perros   | sold   |

  @CP04_Pet
  Scenario: Eliminar una mascota exitosamente
    Given el actor establece el endpoint de petstore
    And el actor crea una mascota con id "987654323" nombre "Luna" categoria "Gatos" y estado "available"
    When el actor elimina la mascota creada
    Then el codigo de respuesta debe ser 200
    And el campo "message" de la respuesta debe coincidir con el id creado

  @CP05_Pet
  Scenario: Consultar una mascota con un id inexistente
    Given el actor establece el endpoint de petstore
    When el actor consulta la mascota con id "999999999888"
    Then el codigo de respuesta debe ser 404
    And el campo "type" de la respuesta debe ser "error"
    And el campo "message" de la respuesta debe ser "Pet not found"
