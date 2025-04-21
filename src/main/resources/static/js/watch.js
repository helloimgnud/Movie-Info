let thisId;

document.addEventListener("DOMContentLoaded", async () => {
  
  const moviePlayerSection = document.getElementById("moviePlayerSection");

  fetchMovieInfo().then(()=>{
    const playNowBtn = document.getElementById("playNowBtn");
    playNowBtn.addEventListener("click", () => {
      if (moviePlayerSection.style.display === "none") {
        moviePlayerSection.style.display = "block"; // Show the movie player section
        moviePlayerSection.scrollIntoView({ behavior: "smooth" }); // Scroll to the section
      }
    });

    const addFavBtn = document.getElementById("addFav");
    addFavBtn.addEventListener("click", async () => {
      
    try {
      const response = await fetch("/account/fav/" + thisId, {
        method: "POST",  
        headers: {
          "Content-Type": "application/json",  
        }, 
      });

      if (!response.ok) {
        throw new Error("Failed to update account info.");
      }
      else{
        alert("Add successfully!");
      }
    }
    catch (error) {
      console.error("Error ", error);
      alert("An error occurred. Please try again.");
    }
  });

  fetchMovieUrl().then((url) =>{
      console.log(url.videoUrl);
      const videoUrl = url.videoUrl; 
      document.getElementById('movieSource').src = videoUrl;
      document.getElementById('moviePlayer').load();
  });
  });
});

async function fetchMovieUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    let id;
      if (urlParams.has('movieId')) {
        id = urlParams.get("movieId"); 
      } else { alert("No id in URL Params");}
    try {
    const response = await fetch('/watch?movieId=' + id);
    const movieUrl = await response.json();

    return movieUrl;
  } catch (error) {
    console.error('Error fetching movies:', error);
  }
}

async function fetchMovieInfo(){
  const urlParams = new URLSearchParams(window.location.search);
  let id;
    if (urlParams.has('movieId')) {
      id = urlParams.get("movieId"); 
      thisId = id;
    } else { alert("No id in URL Params");}

  try {
    const response = await fetch('/movies/' + id);
    const movie = await response.json();

    const movieDetails = {
      id: movie.movieId,
      title: movie.title,
      releaseDate: movie.releaseDate,
      views: movie.view,
      rating: movie.imdbRating,
      posterPath:movie.posterPath,
      description:movie.description,
      directors: movie.directors.map(g => g.directorName),
      genres: movie.genres.map(g => g.genreName),
      actors: movie.actors.map(g => g.actorName)
    }

    console.log(movieDetails);

    const movieDetailContainer = document.getElementById('movieDetailContainer');
    const movieDetailHTML = `
      <section class="movie-detail py-5">
        <div class="container">
          <div class="row">
            <!-- Movie Poster -->
            <div class="col-md-4">
              <img src="${movieDetails.posterPath}" alt="${movieDetails.title}" class="img-fluid rounded">
            </div>
            <!-- Movie Info -->
            <div class="col-md-8 text-white">
              <h1 class="mb-3">${movieDetails.title}</h1>
              <p class="lead">${movieDetails.description}</p>
              <p><strong>Rating:</strong> ${movieDetails.rating}</p>
              <p><strong>Genre:</strong> ${movieDetails.genres}</p>
              <p><strong>Director:</strong> ${movieDetails.directors}</p>
              <p><strong>Actor:</strong> ${movieDetails.actors}</p>
              <p><strong>Release Date:</strong> ${movieDetails.releaseDate}</p>
              <div class="d-flex gap-3 mt-3">
                <button id="playNowBtn" class="btn btn-primary btn-lg">
                  <i class="bi bi-play-circle me-2"></i>Play Now
                </button>
                <button id ="addFav" class="btn btn-danger btn-lg">
                  <i class="bi bi-star-fill me-2"></i>Add to Favorites
                </button>
              </div>
            </div>
          </div>
        </div>
      </section>
    `;
    movieDetailContainer.innerHTML = movieDetailHTML;

  } catch (error) {
    console.error('Error fetching movies:', error);
  }
}