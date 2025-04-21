document.addEventListener("DOMContentLoaded", async () => {
  // init();

  fetchTopRating().then((movies) =>{
    const hero = document.getElementById("hero");
    const selectedMovie = movies[0];
    console.log(selectedMovie);
    const movieHero =`
    <section class="hero text-white text-center d-flex align-items-center justify-content-center">
    <div class="container">
      <h1 class="display-4 fw-bold">${selectedMovie.title}</h1>
      <p class="lead">${selectedMovie.description}</p>
      <div class="mt-4">
        <button class="btn btn-danger btn-lg me-2"
        id="${selectedMovie.movieId}"
        onclick="watchMovieId('${selectedMovie.movieId}')">PLAY NOW</button>
      </div>
    </div>
  </section>`

  hero.innerHTML = movieHero;
  });

  fetchTopMovies().then((movies) => {
    const content = document.getElementById("content");
    
    const movieGallery = `
      <section class="movie-gallery py-5">
        <div class="container">
          <h2 class="text-white mb-4">Popular on StreamFlix</h2>
          <div class="row gy-4">
            ${movies.map(movie => `
              <div class="col-6 col-md-3 d-flex">
                <div class="card bg-dark text-white h-100" id="${movie.movieId}" onclick="watchMovieId('${movie.movieId}')">
                  <img src="${movie.posterPath}" class="card-img-top" alt="${movie.title}">
                  <div class="card-body">
                    <h5 class="card-title">${movie.title}</h5>
                  </div>
                </div>
              </div>
            `).join('')}
          </div>
        </div>
      </section>
    `;

    content.innerHTML = movieGallery;
  });
});

function watchMovieId(movieId) {
  console.log(`Movie ID: ${movieId}`);
  window.location.href = '/user/watch?movieId=' + movieId;
}

async function fetchTopRating(){
  try {
    const response = await fetch("http://localhost:8080/movies/top/rating/1");
    if (!response.ok) {
      throw new Error("Failed to fetch movies");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching top raing movies:", error);
    return [];
  }
}

async function fetchTopMovies() {
  try {
    const response = await fetch("http://localhost:8080/movies/top/view/4");
    if (!response.ok) {
      throw new Error("Failed to fetch movies");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching top movies:", error);
    return [];
  }
}