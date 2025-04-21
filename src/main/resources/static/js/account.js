const itemsPerPage = 4; 
let currentPage = 1; 

document.addEventListener("DOMContentLoaded", async () => {
  fetchInfo().then(()=>{
    init();
    changeInfoSubmit();
    changePasswordSubmit();
  });
  fetchMovies();
});

function formatDate(dateString) {
  const options = { year: "numeric", month: "long", day: "numeric" };
  const date = new Date(dateString);
  return date.toLocaleDateString(undefined, options);
}

function init() {
  const changeInfoBtn = document.getElementById("changeInfoBtn");
  const changePasswordBtn = document.getElementById("changePasswordBtn");
  const formOverlay = document.getElementById("formOverlay");
  const closeFormBtn = document.getElementById("closeFormBtn");
  const changeInfoForm = document.getElementById("changeInfoForm");
  const changePasswordForm = document.getElementById("changePasswordForm");

  changeInfoBtn.addEventListener("click", () => {
    formOverlay.classList.remove("d-none");
    changeInfoForm.classList.remove("d-none");
    changePasswordForm.classList.add("d-none");
  });

  changePasswordBtn.addEventListener("click", () => {
    formOverlay.classList.remove("d-none");
    changePasswordForm.classList.remove("d-none");
    changeInfoForm.classList.add("d-none");
  });

  closeFormBtn.addEventListener("click", () => {
    formOverlay.classList.add("d-none");
    changeInfoForm.classList.add("d-none");
    changePasswordForm.classList.add("d-none");
  });
}

async function fetchInfo(){
  try {
    const response = await fetch("/account/info");

    if (!response.ok) {
      throw new Error("Failed to fetch account info.");
    }

    const accountData = await response.json();

    const accountSection = document.querySelector(".account-section .card");
    accountSection.innerHTML = `
      <h5 class="mb-3">User Information</h5>
      <p><strong>Username:</strong> ${accountData.username || "N/A"}</p>
      <p><strong>Name:</strong> ${accountData.name || "N/A"}</p>
      <p><strong>Date of Birth:</strong> ${
        accountData.dateOfBirth ? formatDate(accountData.dateOfBirth) : "N/A"
      }</p>
      <p><strong>Email:</strong> ${accountData.email || "N/A"}</p>
      <p><strong>Phone Number:</strong> ${accountData.phoneNumber || "N/A"}</p>
      <div class="mt-4">
        <button id="changeInfoBtn" class="btn btn-primary me-2">Change Info</button>
        <button id="changePasswordBtn" class="btn btn-secondary">Change Password</button>
      </div>
    `;
    document.getElementById("name").value = accountData.name || "";
    document.getElementById("dob").value = accountData.dateOfBirth
      ? formatDateForInput(accountData.dateOfBirth)
      : "";
    document.getElementById("email").value = accountData.email || "";
    document.getElementById("phone").value = accountData.phoneNumber || "";
  } catch (error) {
    console.error("Error loading account info:", error);
  }
}

function formatDateForInput(dateString) {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
}

async function changeInfoSubmit(){
  document.getElementById("changeInfoForm").addEventListener("submit", async function(event) {
    event.preventDefault(); 

    const name = document.getElementById("name").value;
    const dob = document.getElementById("dob").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;

    const updatedInfo = {
      name: name,
      dateOfBirth: dob,
      email: email,
      phoneNumber: phone
    };

    try {
      const response = await fetch("/account/info", {
        method: "POST",  
        headers: {
          "Content-Type": "application/json",  
        },
        body: JSON.stringify(updatedInfo)  
      });

      if (!response.ok) {
        throw new Error("Failed to update account info.");
      }

      alert("Account information updated successfully!");

      const formOverlay = document.getElementById("formOverlay");
      const changeInfoForm = document.getElementById("changeInfoForm");
      formOverlay.classList.add("d-none");
      changeInfoForm.classList.add("d-none");
      fetchInfo();

    } catch (error) {
      console.error("Error updating account info:", error);
      alert("An error occurred. Please try again.");
    }
  });
}

async function changePasswordSubmit(){
  const changePasswordForm = document.getElementById("changePasswordForm");

  changePasswordForm.addEventListener("submit", async (event) => {
    event.preventDefault(); 

    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (newPassword !== confirmPassword) {
      alert("New password and confirm password do not match.");
      return;
    }

    const changePasswordData = {
      currentPassword: currentPassword,
      newPassword: newPassword,
    };

    try {
      const response = await fetch("/account/changePassword", {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(changePasswordData),
      });

      if (!response.ok) {
        throw new Error("Failed to change password.");
      }

      alert("Password changed successfully!");
      window.location.href = "/logout"; 

    } catch (error) {
      console.error("Error changing password:", error);
      alert("There was an error changing your password. Please try again.");
    }
  });
}

function displayMovies(movieList) {
  const movieContainer = document.getElementById('movieContainer');
  movieContainer.innerHTML = ""; // Clear previous content

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
    const response = await fetch('/account/fav');
    const movieData = await response.json();
    let movies = [];
    movieData.forEach((movie) => {
      movies.push({
        id: movie.movieId,
        title: movie.title,
        posterPath: movie.posterPath,
      });
    });

    displayMovies(movies);
  } catch (error) {
    console.error('Error fetching movies:', error);
  }
}
