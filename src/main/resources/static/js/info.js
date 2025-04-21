
document.addEventListener("DOMContentLoaded", async () => {    

  fetchMovies().then((fetchString) => {
    console.log(fetchString);
    fetchInfo(fetchString);
  });
});

function displayMovies(movieList) {
  const movieContainer = document.getElementById('movieContainer');
  console.log(movieList);
  movieContainer.innerHTML = movieList
    .map(
      (movie) => `
      <div class="col-md-3" id="${movie.movieId}" onclick="watchMovieId('${movie.movieId}')">
        <div class="card">
          <img src="${movie.posterPath}" class="card-img-top" alt="${movie.title}">
          <div class="card-body">
            <h5 class="card-title">${movie.title}</h5>
          </div>
        </div>
      </div>
    `
    )
    .join('');
}

async function fetchMovies() {
  const urlParams = new URLSearchParams(window.location.search);
  let id;
  if (urlParams.has('actor')) {
    id = urlParams.get("actor"); 

    try {
      const response = await fetch('http://localhost:8080/actors/' + id);
      const actorData = await response.json();
      const movieList = actorData.movies;
      document.getElementById('infoName').textContent = actorData.actorName;

      displayMovies(movieList);
      return "actor?id=" + id;
    } catch (error) {
      console.error('Error fetching movies:', error);
    }
  } 
  else if (urlParams.has('director')) {
    id = urlParams.get("director"); 

    try {
      const response = await fetch('http://localhost:8080/directors/' + id);
      const directorData = await response.json();
      const movieList = directorData.movies;
      document.getElementById('infoName').textContent = directorData.directorName;

      displayMovies(movieList);
      return "director?id=" + id;
    } catch (error) {
      console.error('Error fetching movies:', error);
    }
  }
  else { alert("No id in URL Params");}

}

async function fetchInfo(fetchString){
  try {
      const response = await fetch('http://localhost:8080/getInfo/' + fetchString);
      const infoData = await response.json();
      console.log(infoData);
      if(infoData.imageUrl === null){
        document.getElementById('infoPoster').src = "https://placehold.co/400x500?text=Not+Found";
      }
      else{
        document.getElementById('infoPoster').src = infoData.imageUrl;
      }

      if(infoData.biography === null){
        document.getElementById('infoBio').textContent = "There is no biography available at the moment. Please try again later!";
      }
      else{
        document.getElementById('infoBio').textContent = infoData.biography;
      }


    } catch (error) {
      console.error('Error fetching movies:', error);
    }
}

function watchMovieId(movieId) {
  console.log(`Movie ID: ${movieId}`);
  window.location.href = '/user/watch?movieId=' + movieId;
}
