import java.util.Scanner;

class Personaje {//creamos la clase personaje
    String nombre;
    int fuerza;
    int velocidad;
    int vida_hp;
    int vidaMaxima;
    boolean defendiendo = false; // Variable para controlar la defensa
    int contRecuperar = 3; // Contador de recuperaciones disponibles
    //Creamos un constructor de la clase personaje
    public Personaje(String nombre, int fuerza, int velocidad, int vida_hp) {
        this.nombre = nombre;// Inicializar
        this.fuerza = fuerza;// Inicializar
        this.velocidad = velocidad;// Inicializar
        this.vida_hp = vida_hp;// Inicializar
        this.vidaMaxima = vida_hp; // Inicializar vida máxima con vida inicial
    }

    public void mostrarInfo() {//Declaracion del metodo para mostrar informacion
        System.out.println("Nombre: " + nombre);
        System.out.println("Fuerza: " + fuerza);
        System.out.println("Velocidad: " + velocidad);
        System.out.println("Vida Máxima: " + vidaMaxima);
        System.out.println("Recuperaciones restantes: " + contRecuperar);
    }

    public void defender() {//Metodo para definir que un personaje se esta defendiendo
        System.out.println(this.nombre + " se está defendiendo.");
        defendiendo = true;
    }

    public void atacar(Personaje enemigo) {//Metodo para definir como un personaje puede infligar daño a otro
        enemigo.recibirDaño(this.fuerza);
    }

    public int calcularDañoReducido(int daño) {//Metodo que se usara para calcular el daño recibido
        return daño;
    }

    public void recibirDaño(int daño) {//Metodo para recibir daño en funcion de la defensa
        if (defendiendo) {
            daño = calcularDañoReducido(daño);
        }
        this.vida_hp -= daño;//Parte del metodo que se encarga de restar la vida del personaje segun el daño
        System.out.println(this.nombre + " ha recibido " + daño + " puntos de daño. Vida restante: " + this.vida_hp);
        defendiendo = false; // Resetear estado de defensa después de recibir daño
    }

    public void recuperar() {//Metodo para recuperar vida
        if (contRecuperar > 0) {
            int recuperarHp = 15;//lo que te recupera de vida al usar ese moviemiento
            this.vida_hp += recuperarHp;//suma la vida recuperada a la vida actual
            if (this.vida_hp > this.vidaMaxima) {
                this.vida_hp = this.vidaMaxima; // Asegurarse de no superar la vida máxima
            }
            contRecuperar--; // Decrementar el contador de recuperaciones
            System.out.println(this.nombre + " ha recuperado " + recuperarHp + " puntos de vida. Vida actual: " + this.vida_hp);
            System.out.println("Recuperaciones restantes: " + contRecuperar);
        } else {
            System.out.println(this.nombre + " ya no puede recuperar más vida.");
        }
    }

    public void ataqueEspecial(Personaje enemigo) {//Metodo para el ataque especial del personaje
        int dañoEspecial = this.fuerza * 2;//Aumenta la fuerza al doble
        enemigo.recibirDaño(dañoEspecial);
        System.out.println(this.nombre + " ha realizado un ataque especial causando " + dañoEspecial + " puntos de daño.");
    }
}//fin de la clase personaje


class SuperHero extends Personaje {//Inicio de la Clase para héroes
    public SuperHero(String nombre, int fuerza, int velocidad, int vida_hp) {
        super(nombre, fuerza, velocidad, vida_hp);
    }

    public void salvar() {//Metodo para imprimir el salvar de los heroes
        System.out.println(nombre + " está salvando el día!");
    }

    @Override//indica que el metodo se sobreescribe un metodo de la superclase (Personaje)
    public int calcularDañoReducido(int daño) {
        return (int) (daño * 0.2); // Reduce el daño en un 80%
    }
}//Fin de la clase SuperHero


class Villano extends Personaje {//Inicio de la Clase para villano
    public Villano(String nombre, int fuerza, int velocidad, int vida_hp) {
        super(nombre, fuerza, velocidad, vida_hp);
    }

    public void atacar() {//Metodo para imprimir el ataque de los villanos
        System.out.println(nombre + " está atacando!");
    }

    @Override
    public int calcularDañoReducido(int daño) {
        return (int) (daño * 0.4); // Reduce el daño en un 60%
    }
}


public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Crear personajes disponibles
        SuperHero[] heroes = {//Se crean los arrays segun el orden del constructor SuperHero
            new SuperHero("Superman", 30, 80, 100),
            new SuperHero("Batman", 15, 60, 100),
            new SuperHero("Wonder Woman", 25, 70, 100)
        };

        Villano[] villanos = {//Se crean los arrays segun el orden del constructor Villano
            new Villano("Lex Luthor", 15, 60, 150),
            new Villano("Joker", 10, 50, 150),
            new Villano("Cheetah", 20, 65, 150)
        };

        // Menú de selección de personajes
        System.out.println("Selecciona tu héroe:");
        for (int i = 0; i < heroes.length; i++) {
            System.out.println((i + 1) + ". " + heroes[i].nombre);
        }
        int eleccionHeroe = scanner.nextInt() - 1;//se resta 1 para que coincida con el indice del array
        SuperHero heroe = heroes[eleccionHeroe];

        System.out.println("Selecciona tu villano:");
        for (int i = 0; i < villanos.length; i++) {
            System.out.println((i + 1) + ". " + villanos[i].nombre);
        }
        int eleccionVillano = scanner.nextInt() - 1;
        Villano villano = villanos[eleccionVillano];

        System.out.println("¡Comienza el combate por turnos!");
        heroe.mostrarInfo();//Llama al metodo de mostrarInfo para ver las estadisticas
        villano.mostrarInfo();

        boolean turnoHeroe = true; // Controla de quién es el turno

        while (heroe.vida_hp > 0 && villano.vida_hp > 0) {//Se ejecuta mientras la vida los personajes sea > 0
            if (turnoHeroe) {//Verifica si es el turno del heroe
                //Imprime el menu de acciones
                System.out.println("Turno del héroe. Elige una acción: 1. Atacar 2. Defender 3. Recuperar 4. Ataque Especial");
                int accion = scanner.nextInt();
                switch (accion) {
                    case 1://Si presionas 1 el heroe ataca
                        heroe.atacar(villano);
                        break;
                    case 2://Si presionas 2 el heroe se defiende
                        heroe.defender();
                        break;
                    case 3://Si presionas 3 el heroe recupera vida
                        heroe.recuperar();
                        break;
                    case 4://Si presionas 4 el heroe hace ataque especial
                        heroe.ataqueEspecial(villano);
                        break;
                    default:
                        System.out.println("Acción no válida. Pierdes tu turno.");//Por si el usuario se hace el chistoso
                        break;
                }
            } else {
                System.out.println("Turno del villano. Elige una acción: 1. Atacar 2. Defender 3. Recuperar 4. Ataque Especial");
                int accion = scanner.nextInt();
                switch (accion) {
                    case 1://Si presionas 1 el villano ataca
                        villano.atacar(heroe);
                        break;
                    case 2://Si presionas 2 el villano se defiende
                        villano.defender();
                        break;
                    case 3://Si presionas 3 el villano recupera vida
                        villano.recuperar();
                        break;
                    case 4://Si presionas 4 el villano hace ataque especial
                        villano.ataqueEspecial(heroe);
                        break;
                    default:
                        System.out.println("Acción no válida. Pierdes tu turno.");//Por si el usuario se hace el chistoso
                        break;
                }
            }

            turnoHeroe = !turnoHeroe; // Alterna el turno cambiando el estado a False
        }

        if (heroe.vida_hp > 0) {//Condicion para imprimir el ganador
            System.out.println(heroe.nombre + " ha ganado!");
        } else {
            System.out.println(villano.nombre + " ha ganado!");
        }
        scanner.close();//Esto es para que no me salga el warning ese del scanner
    }
}