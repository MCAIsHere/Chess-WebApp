// Dark/Light mode
window.onload = function (){
    if (localStorage.getItem("Screen") === "light") {
        document.body.style.backgroundColor = "#d3d3d3";
        document.getElementById("game-statistics-p").style.color = "#1a1a1a"
    } else if (localStorage.getItem("Screen") === "dark") {
        document.body.style.backgroundColor = "#1a1a1a";
        document.getElementById("game-statistics-p").style.color = "#fcfcfc"
    }

    document.getElementById("UI-COLOR").onclick = function () {
        const currentBg = getComputedStyle(document.body).backgroundColor;
        if (currentBg === "rgb(211, 211, 211)") {  // Dark background
            document.body.style.backgroundColor = "#1a1a1a";
            document.getElementById("game-statistics-p").style.color = "#fcfcfc"
            localStorage.setItem("Screen", "dark");
        } else {  // Light background
            document.body.style.backgroundColor = "#d3d3d3";
            document.getElementById("game-statistics-p").style.color = "#1a1a1a"
            localStorage.setItem("Screen", "light");
        }
    };
}