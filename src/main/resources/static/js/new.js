function showVideo(videoFile) {
  const videoPlayer = document.getElementById("videoPlayer");
  const videoSource = document.getElementById("videoSource");

  videoSource.src = videoFile;
  videoPlayer.load(); 
  const videoModal = new bootstrap.Modal(document.getElementById("videoModal"));
  videoModal.show();
}
