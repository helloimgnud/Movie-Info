let isAscending = true;
const itemsPerPage = 10;
let currentPage = 1;
let movies = [];


document.addEventListener("DOMContentLoaded", async () => {
  fetchGenres().then(() => {
    fetchMovies().then(()=>{
      applyUrlFilter();
    });
  });
});


document.getElementById('sortOrderBtn').addEventListener('click', () => {
  isAscending = !isAscending;
  document.getElementById('sortOrderBtn').innerText = isAscending ? '▲' : '▼';
  filterAndSortMovies();
});

// Listen for changes on the filter fields for immediate update
document.getElementById('title').addEventListener('input', filterAndSortMovies);
document.getElementById('releaseDate').addEventListener('input', filterAndSortMovies);
document.getElementById('genre').addEventListener('change', filterAndSortMovies);
document.getElementById('sortBy').addEventListener('change', filterAndSortMovies);

function filterAndSortMovies() {
  const title = document.getElementById('title').value.toLowerCase();
  const releaseDate = document.getElementById('releaseDate').value;
  const genre = document.getElementById('genre').value.toLowerCase();
  const sortBy = document.getElementById('sortBy').value;

  // Filter the movies based on title, release date, and genre
  const filteredMovies = movies.filter((movie) => {
    const matchesTitle = movie.title.toLowerCase().includes(title);
    const matchesReleaseDate = releaseDate ? movie.releaseDate.startsWith(releaseDate) : true;
    const matchesGenre = genre ? movie.genres.some(g => g.toLowerCase() === genre) : true;

    return matchesTitle && matchesReleaseDate && matchesGenre;
  });

  // Sort the filtered movies based on the selected criteria
  const sortedMovies = filteredMovies.sort((a, b) => {
    if (sortBy === 'views') {
      return isAscending ? a.views - b.views : b.views - a.views;
    } else if (sortBy === 'rating') {
      return isAscending ? a.rating - b.rating : b.rating - a.rating;
    }
  });

  currentPage = 1; // Reset to the first page after applying filters
  displayMovies(sortedMovies);
}

function displayMovies(movieList) {
  const movieContainer = document.getElementById('movieContainer');
  movieContainer.innerHTML = ""; // Clear previous content

  // Paginate the movie list
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const paginatedMovies = movieList.slice(startIndex, endIndex);

  paginatedMovies.forEach((movie) => {
    movieContainer.innerHTML += `
      <div class="col-md-3" id="${movie.id}" onclick="watchMovieId('${movie.id}')">
        <div class="card">
          <img src="${movie.posterPath}" class="card-img-top" alt="${movie.title}">
          <div class="card-body">
            <h5 class="card-title">${movie.title}</h5>
            <p class="card-text">Rating: ${movie.rating} | Views: ${movie.views}</p>
          </div>
        </div>
      </div>
    `;
  });

  renderPagination(movieList.length);
}

function renderPagination(totalMovies) {
  const paginationContainer = document.getElementById("paginationContainer");
  const totalPages = Math.ceil(totalMovies / itemsPerPage);

  // Create pagination buttons
  paginationContainer.innerHTML = "";
  for (let i = 1; i <= totalPages; i++) {
    const pageButton = document.createElement("button");
    pageButton.classList.add("btn", "btn-outline-light", "me-2");
    pageButton.textContent = i;
    pageButton.onclick = () => {
      currentPage = i;
      displayMovies(movies);
    };
    paginationContainer.appendChild(pageButton);
  }
}

function watchMovieId(movieId) {
  console.log(`Movie ID: ${movieId}`);
  window.location.href = '/user/watch?movieId=' + movieId;
}

async function fetchMovies() {
  try {
    const response = await fetch('http://localhost:8080/movies');
    const movieData = await response.json();

    movieData.forEach((movie) => {
      movies.push({
        id: movie.movieId,
        title: movie.title,
        releaseDate: movie.releaseDate,
        views: movie.view,
        rating: movie.imdbRating,
        posterPath: movie.posterPath,
        genres: movie.genres.map(g => g.genreName),
      });
    });

    displayMovies(movies);
  } catch (error) {
    console.error('Error fetching movies:', error);
  }
}



async function fetchGenres() {
  try {
    const response = await fetch('http://localhost:8080/genres');
    const genresData = await response.json();

    populateGenreDropdown(genresData);
  } catch (error) {
    console.error('Error fetching genres:', error);
  }
}

function populateGenreDropdown(genres) {
  const genreSelect = document.getElementById('genre');
  
  genreSelect.innerHTML = '<option value="">All Genres</option>';

  genres.forEach(genre => {
    const option = document.createElement('option');
    option.value = genre.genreName.toLowerCase(); // Convert genre name to lowercase for matching
    option.textContent = genre.genreName;
    genreSelect.appendChild(option);
  });
}

function applyUrlFilter() {
  const urlParams = new URLSearchParams(window.location.search);
  const genreParam = urlParams.get('genre'); // Get the 'genre' query parameter

  if (genreParam) {
    const genreSelect = document.getElementById('genre');
    genreSelect.value = genreParam.toLowerCase(); // Set the value to the genre in URL (make sure case-insensitive matching)
    console.log(genreParam);
    filterAndSortMovies();
  }else{
    console.log("not genre");
  }
}