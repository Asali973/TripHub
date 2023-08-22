import flatpickr from 'flatpickr';


// Wait for the DOM to load
document.addEventListener('DOMContentLoaded', () => {
    const toggleInput = document.querySelector('.toggle-input');
    const toggleLabel = document.querySelector('.toggle-label');
    const descriptionTextarea = document.getElementById('description');
    // Add an event listener to update the character count as the user types
        descriptionTextarea.addEventListener("input", function() {
            updateCharCount(descriptionTextarea);
        });

    // Event listener for the toggle label
    toggleLabel.addEventListener('click', () => {
        toggleInput.checked = !toggleInput.checked;
    });

    // Initialize flatpickr for availableFrom date
    flatpickr("#availableFromPicker", {
        dateFormat: "Y-m-d", // Format the date as "YYYY-MM-DD"
        required: true
    });

    // Initialize flatpickr for availableTil date
    flatpickr("#availableTillPicker", {
        dateFormat: "Y-m-d", // Format the date as "YYYY-MM-DD"
        required: true
    });
//document.addEventListener("DOMContentLoaded", function() {
//    // Initialize flatpickr for availableFromPicker
//    flatpickr("#availableFromPicker", {
//        dateFormat: "Y-m-d",
//        required: true,
//        onChange: function(selectedDates, dateStr) {
//            document.getElementById("hiddenAvailableFrom").value = dateStr;
//        }
//    });
//    // Initialize flatpickr for availableTillPicker
//    flatpickr("#availableTillPicker", {
//        dateFormat: "Y-m-d",
//        required: true,
//        onChange: function(selectedDates, dateStr) {
//            document.getElementById("hiddenAvailableTill").value = dateStr;
//        }
//    });
//});
    // Function to update character count for a textarea
function updateCharCount(textarea) {
        const maxLength = 500;
        const remainingChars = maxLength - textarea.value.length;
        
        const charCountElement = document.getElementById("charCount");
        charCountElement.textContent = remainingChars + " characters remaining";
        
        // Disable input when maximum limit is reached
        if (remainingChars < 0) {
            textarea.value = textarea.value.substring(0, maxLength);
            charCountElement.style.color = "red"; // Indicate exceeded limit
        } else {
            charCountElement.style.color = ""; // Reset color
        }
    }

    });