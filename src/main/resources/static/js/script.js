const htmlTag = document.querySelector("html");
const theme_button = document.querySelector(".theme_button");

let currentTheme = getThemeType();
console.log(currentTheme);
// Step 1: set theme to localStorage
function setThemeType(theme) {
  localStorage.setItem("theme", theme);
}
// Step 2: get the theme from localStorage
function getThemeType() {
  let theme = localStorage.getItem("theme");
  return theme ? theme : "light";
}

// step 3: change theme when document(content of HTML) fully Loaded .
document.addEventListener("DOMContentLoaded", () => {
  // we change theme, Initially called when page loaded.
  changeTheme();
});

function changeTheme() {
  // set initial theme for webpage
  htmlTag.classList.add(currentTheme);

  // Action performed when click on theme button
  theme_button.addEventListener("click", () => {
    const oldTheme = currentTheme;
    if (currentTheme == "light") {
      currentTheme = "dark";
      //   theme_button.style.color = "white";
      //   setThemeType(currentTheme);
      //   htmlTag.classList.remove(oldTheme);
      //   htmlTag.classList.add(currentTheme);
    } else {
      currentTheme = "light";
      //   theme_button.style.color = "black";
    }
    setThemeType(currentTheme);
    htmlTag.classList.remove(oldTheme);
    htmlTag.classList.add(currentTheme);
  });
}
