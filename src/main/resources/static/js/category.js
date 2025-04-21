document.addEventListener("DOMContentLoaded", async () => {
  fetchGenres();
});

async function fetchGenres() {
  try {
    const response = await fetch('http://localhost:8080/genres');
    const data = await response.json();

    display(data);
  } catch (error) {
    console.error('Error fetching genres:', error);
  }
}

function display(data){
  const categoriesContainer = document.getElementById("categoriesContainer");

  data.forEach(category => {
    const card = document.createElement("div");
    card.classList.add("col-md-3");

    card.innerHTML = `
      <div class="card text-center" onclick="showGenre('${category.genreName}')">
        <div class="card-body" ">
          <div class="category-icon mb-3">
          </div>
          <h5 class="card-title">${category.genreName}</h5>
  \      </div>
      </div>
    `;

    categoriesContainer.appendChild(card);
});
}

function showGenre(genre) {
  console.log(genre);
  window.location.href = '/user/movies_user?genre=' + genre;
}


