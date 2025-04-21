document.addEventListener("DOMContentLoaded", async () => {
  await fetchActorsAndDirectors();
  setupFilters();
});

let combinedList = [];
let filteredList = [];
const itemsPerPage = 12; 
let currentPage = 1; 
let sortOrder = "asc"; 

async function fetchActorsAndDirectors() {
  try {
    const [actorsResponse, directorsResponse] = await Promise.all([
      fetch("/actors"),
      fetch("/directors"),
    ]);

    const actors = await actorsResponse.json();
    const directors = await directorsResponse.json();

    combinedList = [
      ...actors.map((actor) => ({ ...actor, type: "actor" })),
      ...directors.map((director) => ({ ...director, type: "director" })),
    ];

    filteredList = [...combinedList]; 
    displayCombinedList(filteredList, 1);
  } catch (error) {
    console.error("Error fetching actors or directors:", error);
  }
}

function setupFilters() {
  const nameInput = document.getElementById("name");

  nameInput.addEventListener("input", () => {
    applyFilters(); 

  const roleSelect = document.getElementById("role");
  roleSelect.addEventListener("change", applyFilters);

  const sortOrderBtn = document.getElementById("sortOrderBtn");
  sortOrderBtn.addEventListener("click", () => {
    sortOrder = sortOrder === "asc" ? "desc" : "asc";
    sortOrderBtn.textContent = sortOrder === "asc" ? "▲" : "▼";
    applyFilters();
  });

  const sortBySelect = document.getElementById("sortBy");
  sortBySelect.addEventListener("change", applyFilters);

  const filterForm = document.getElementById("filterForm");
  filterForm.addEventListener("reset", () => {
    filteredList = [...combinedList];
    currentPage = 1;
    displayCombinedList(filteredList, 1);
  });
});
}

function applyFilters() {
  const nameFilter = document.getElementById("name").value.trim().toLowerCase();
  const roleFilter = document.getElementById("role").value;
  const sortBy = document.getElementById("sortBy").value;

  filteredList = combinedList.filter((item) => {
    const matchesName = item.type === "actor" 
      ? item.actorName.toLowerCase().includes(nameFilter)
      : item.directorName.toLowerCase().includes(nameFilter);

    const matchesRole = roleFilter === "" || item.type === roleFilter;

    return matchesName && matchesRole;
  });

  filteredList.sort((a, b) => {
    let valueA, valueB;

    if (sortBy === "name") {
      valueA = a.type === "actor" ? a.actorName : a.directorName;
      valueB = b.type === "actor" ? b.actorName : b.directorName;
    } else if (sortBy === "movies") {
      valueA = a.moviesCount || 0; 
      valueB = b.moviesCount || 0;
    }

    if (valueA < valueB) return sortOrder === "asc" ? -1 : 1;
    if (valueA > valueB) return sortOrder === "asc" ? 1 : -1;
    return 0;
  });

  currentPage = 1;
  displayCombinedList(filteredList, 1);
}

function displayCombinedList(list, page) {
  const listContainer = document.getElementById("actor-director-list");
  listContainer.innerHTML = ""; 

  const startIndex = (page - 1) * itemsPerPage;
  const endIndex = startIndex + itemsPerPage;
  const paginatedList = list.slice(startIndex, endIndex);

  paginatedList.forEach((person) => {
    const card = document.createElement("div");
    card.className = "col-md-3";
    card.setAttribute("id", person.type === "actor" ? person.actorId : person.directorId);

    if (person.type === "actor") {
      card.setAttribute("onclick", `infoActor('${person.actorId}')`);
    } else if (person.type === "director") {
      card.setAttribute("onclick", `infoDirector('${person.directorId}')`);
    }

    card.innerHTML = `
      <div class="card text-center">
        <div class="card-body">
          <div class="image-container mb-3">
            <div class="spinner-border text-light" role="status">
              <span class="visually-hidden">Loading...</span>
            </div>
          </div>
          <h5 class="card-title">${person.type === "actor" ? person.actorName : person.directorName}</h5>
          <p class="card-text">${person.type === "actor" ? "Actor" : "Director"}</p>
        </div>
      </div>
    `;

    listContainer.appendChild(card);

    fetchImageForCard(person, card.querySelector(".image-container"));
  });

  renderPagination(list.length, page);
}

async function fetchImageForCard(person, imageContainer) {
  const url =
    person.type === "actor"
      ? `/getInfo/actor?id=${person.actorId}`
      : `/getInfo/director?id=${person.directorId}`;

  try {
    const response = await fetch(url);
    const data = await response.json();

    imageContainer.innerHTML = `
      <img src="${data.imageUrl}" alt="${person.type === "actor" ? person.actorName : person.directorName}" class="img-fluid rounded" style="max-height: 150px;">
    `;
  } catch (error) {
    console.error(`Error fetching image for ${person.type}:`, error);
    imageContainer.innerHTML = `<p class="text-danger">Image unavailable</p>`;
  }
}


function renderPagination(totalItems, currentPage) {
  const paginationContainer = document.getElementById("pagination");
  paginationContainer.innerHTML = "";

  const totalPages = Math.ceil(totalItems / itemsPerPage);

  const prevButton = document.createElement("button");
  prevButton.className = "btn btn-secondary me-2";
  prevButton.innerText = "Previous";
  prevButton.disabled = currentPage === 1;
  prevButton.onclick = () => changePage(currentPage - 1);
  paginationContainer.appendChild(prevButton);

  for (let i = 1; i <= totalPages; i++) {
    const pageButton = document.createElement("button");
    pageButton.className = `btn ${i === currentPage ? "btn-primary" : "btn-secondary"} me-2`;
    pageButton.innerText = i;
    pageButton.onclick = () => changePage(i);
    paginationContainer.appendChild(pageButton);
  }

  const nextButton = document.createElement("button");
  nextButton.className = "btn btn-secondary";
  nextButton.innerText = "Next";
  nextButton.disabled = currentPage === totalPages;
  nextButton.onclick = () => changePage(currentPage + 1);
  paginationContainer.appendChild(nextButton);
}

function changePage(page) {
  currentPage = page;
  displayCombinedList(filteredList, page);
}

function infoDirector(id) {
  console.log(`Director ID: ${id}`);
  window.location.href = "/user/info?director=" + id;
}

function infoActor(id) {
  console.log(`Actor ID: ${id}`);
  window.location.href = "/user/info?actor=" + id;
}