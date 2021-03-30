Feature: Login

  @LoginSuccessFul
  Scenario Outline: Test123
    Given usuario abre el sitio web
    When Inserta credenciales
      |testCase|
      |<testCase>|
    Then visualiza el home o texto de bienvenidad
      |testCase|
      |<testCase>|
    Examples:
      | testCase |
      |1|
