window.onload = function (){
    const profile_text = document.getElementsByClassName("profile-text");
    if (localStorage.getItem("Screen") === "light") {
        document.body.style.backgroundColor = "#d3d3d3";
        for (let i = 0; i < profile_text.length; i++) {
            profile_text[i].style.color = "black";
        }
        document.getElementById("profile_header").style.borderBottom = "2px solid black";
    } else if (localStorage.getItem("Screen") === "dark") {
        document.body.style.backgroundColor = "#1a1a1a";
        for (let i = 0; i < profile_text.length; i++) {
            profile_text[i].style.color = "white";
        }
        document.getElementById("profile_header").style.borderBottom = "2px solid #ccc";
    }

    document.getElementById("UI-COLOR").onclick = function () {
        const currentBg = getComputedStyle(document.body).backgroundColor;
        if (currentBg === "rgb(211, 211, 211)") {  // Dark background
            document.body.style.backgroundColor = "#1a1a1a";
            localStorage.setItem("Screen", "dark");
            for (let i = 0; i < profile_text.length; i++) {
                profile_text[i].style.color = "white";
            }
            document.getElementById("profile_header").style.borderBottom = "2px solid #ccc";
        } else {  // Light background
            document.body.style.backgroundColor = "#d3d3d3";
            localStorage.setItem("Screen", "light");
            for (let i = 0; i < profile_text.length; i++) {
                profile_text[i].style.color = "black";
            }
            document.getElementById("profile_header").style.borderBottom = "2px solid black";
        }
    };
}