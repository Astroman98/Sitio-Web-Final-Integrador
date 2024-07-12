
USE series_java;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE TABLE `series` (
  `id_serie` int(11) NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `temporadas` varchar(6) NOT NULL,
  `imagen` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO `series` (`id_serie`, `titulo`, `temporadas`, `imagen`) VALUES
(2, 'Avatar: The Last Airbender', '3', 'aclamada_1.jpg'),
(3, 'Fullmetal Alchemist: Brotherhood', '2', 'aclamada_2.jpg'),
(4, 'Game of Thrones', '8', 'aclamada_3.jpg'),
(5, 'The Twilight Zone', '5', 'aclamada_4.jpg'),
(6, 'Breaking Bad', '5', 'aclamada_5.jpg');



ALTER TABLE `series`
  ADD PRIMARY KEY (`id_serie`);
  
  
  ALTER TABLE `series`
  MODIFY `id_serie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


SELECT * FROM series;
