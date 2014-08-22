

DROP DATABASE IF EXISTS rompepiernas;
CREATE DATABASE IF NOT EXISTS rompepiernas;
USE rompepiernas;

CREATE TABLE cd(    
    titulo VARCHAR(25) NOT NULL PRIMARY KEY,
    autor VARCHAR(25),
    genero VARCHAR(25),
    prestamo VARCHAR(1000)
    );


INSERT INTO cd
 VALUES ('La Cocotera', 'A Saber', 'Geek', 'Se lo prestamos a Paco, el frutero hermano de Manolo el jardinero, el 23/04/1999'),
        ('Full Metal Panic OST1', 'Various', 'Anime', 'Manuela lo tiene desde el 2000'),
        ('Fullmetal Alchemist', 'Various', 'J-POP', 'Alguien lo tendrá, porque yo no lo tengo'),
        ('BraveHeart Soundtrack', 'Soundtrack', 'Soundtrack', 'El carnicero lo tiene y auno no lo ha devuelto, mejor no pedirselo por el tamaño de su cuchillo :S'),
        ('Welcome!', 'UNKNOWN', 'Instrumental', 'Tu suegra lo tiene'),
        ('Otakus dream', 'Shuffle', 'Anime', 'Prueba en la fira del comic'),
        ('Arjuna', 'Various', 'Instrumental', 'Creo que lo tenia tu mujer ....'),
        ('Teruteru Bouzu', 'Various', 'Anime', 'Da igual quién lo tenga, no vale nada'),
        ('Rahxephon', 'Various', 'Instrumental', 'Otro que tiene la suegra, haz como el carnicero'),
        ('Sweet Magic', 'Various', 'Instrumental', 'Tu hijo menor lo tiene para un trabajo del cole'),
        ('Hack OVA', 'Various', 'Ojalá lo supierás', 'Haz como si no existiera'),
        ('Tree', 'Enya', 'New Age', 'José el que se fue para las americas'),
        ('Michi to you all', 'Aluto', 'Anime', 'Nipponsei lo tiene, se encuentra en rizon'),
        ('BoA', 'BoA', 'J-POP', 'Fin lo tiene, no lo devolvera facilmente'),
        ('Blanco y Negro Hits 80', 'Various', 'Various', 'Lo tiene el moreno'),
        ('August Hits', 'DJ JoVy', 'Dance', 'who knows?!'),
        ('Camp Rock', 'Various', 'Rock', 'Seguramente tu hierna'),
        ('July Hits', 'DJ JoVy', 'Dance', 'Pregunta al perro');
        

CREATE USER 'usuario1'@'%' identified BY 'usuario1';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON  rompepiernas.* TO 'usuario1'@'%';