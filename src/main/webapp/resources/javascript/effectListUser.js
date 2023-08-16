document.addEventListener("DOMContentLoaded", function() {
    let tabs = document.querySelectorAll(".nav-link");
    tabs.forEach(tab => {
        tab.addEventListener("mouseover", function() {
            if(!this.classList.contains("active")) {
                this.style.backgroundColor = "#211E85";
            }
        });
        tab.addEventListener("mouseout", function() {
            if(!this.classList.contains("active")) {
                this.style.backgroundColor = "transparent";
            }
        });
    });
});
