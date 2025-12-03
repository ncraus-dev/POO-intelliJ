# POO-intelliJ

Proyecto de práctica en Java centrado en Programación Orientada a Objetos (POO).

## ¿De qué va?
El código principal está en el paquete `etsisi.upm`. Contiene las clases y paquetes que implementan la lógica del proyecto. Abre `src/main/java/etsisi/upm` en tu IDE para ver todos los ficheros.

Clases y paquetes principales:
- Main — punto de entrada (`public static void main`) para ejecutar la aplicación.
- CLI — interfaz de línea de comandos y manejo de interacción con el usuario.
- Testeo — utilidades o ejemplos de pruebas/manual testing incluidos en el repo.
- Display/Logic/Model — subpaquetes:
- Model: clases que representan los objetos usados por la lógica.
- Logic: clases con la lógica o estructuras de objetos del modelo.
- Display: componentes de presentación/visualización.

## Requisitos
- JDK 17+ (se recomienda 21)
- IntelliJ IDEA (Community o Ultimate)
- Maven (el repositorio incluye `pom.xml`)

## Cómo abrir y ejecutar
1. Abre IntelliJ → Open → selecciona la carpeta del proyecto.
2. Configura el Project SDK si hace falta: File → Project Structure → Project SDK.
3. Ejecuta `etsisi.upm.Main` con clic derecho → Run.  
   Alternativamente, si quieres usar Maven:
   - Compilar: `mvn clean compile`
   - Empaquetar: `mvn package`
   - Buscar el jar en los archivos, copiar el path absoluto
   - Ir a target
   - Ejecutar (si está configurado el plugin exec):
     `java -jar Practica-E2_POO-1.0-SNAPSHOT.jar`

## Estructura (resumen)
- src/main/java — código (paquete `etsisi.upm` y subpaquetes Display, Logic, Model)
- src/test/java — tests (si existen)
- pom.xml — configuración Maven
- README.md

## Git — commits (solo lo esencial)
- Branches: `feature/nombre` o `fix/descripcion`.
- Mensaje de commit recomendado: `tipo: resumen breve`  
  Ejemplos: `feat: add new CLI option`, `fix: correct input validation`, `docs: update README`
- Flujo mínimo:
  1. `git checkout -b feature/nombre`
  2. `git add . && git commit -m "tipo: resumen breve"`
  3. `git push origin feature/nombre` → abrir PR contra `main`

Antes de abrir PR, ejecuta la clase principal o los tests relevantes para comprobar que todo funciona.

## Licencia
Uso entre el equipo / práctica.
