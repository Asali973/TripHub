import flatpickr from 'flatpickr';


// Wait for the DOM to load
document.addEventListener('DOMContentLoaded', () => {
    const toggleInput = document.querySelector('.toggle-input');
    const toggleLabel = document.querySelector('.toggle-label');
    const descriptionTextarea = document.getElementById('description');

    // Event listener for the toggle label
    toggleLabel.addEventListener('click', () => {
        toggleInput.checked = !toggleInput.checked;
    });

    // Initialize flatpickr for availableFrom date
    flatpickr("#availableFrom", {
        dateFormat: "Y-m-d", // Format the date as "YYYY-MM-DD"
        required: true
    });

    // Initialize flatpickr for availableTil date
    flatpickr("#availableTill", {
        dateFormat: "Y-m-d", // Format the date as "YYYY-MM-DD"
        required: true
    });

    // Function to update character count for a textarea
    function updateCharCount(textarea) {
        const maxLength = 500;
        const currentLength = textarea.value.length;
        const remainingChars = maxLength - currentLength;

        const charCountElement = document.getElementById('charCount');
        charCountElement.innerText = remainingChars + " characters remaining";
    }

    // Set default value for descriptionTextarea
    var defaultValue = "#{accommodationBean.accommodationVm.description}";
    descriptionTextarea.value = defaultValue;

    // Add event listener for input changes in descriptionTextarea
    descriptionTextarea.addEventListener('input', () => {
        updateCharCount(descriptionTextarea);
    });
    
    // Initial character count update
    updateCharCount(descriptionTextarea);
});