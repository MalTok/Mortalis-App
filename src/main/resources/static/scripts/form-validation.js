$(document).ready(function () {
    let $name = $("#name");
    let $title = $("#title");
    let $funeralDetails = $("#funeralDetails");
    let $additionalInfo = $("#additionalInfo");

    let $button = $("#necrologyFormButton");

    const nameMaxLength = 77;
    const titleMaxLength = 115;
    const funeralDetailsMaxLength = 382;
    const additionalInfoMaxLength = 200;

    let $nameInfo = $("#nameInfo");
    let $titleInfo = $("#titleInfo");
    let $funeralDetailsInfo = $("#funeralDetailsInfo");
    let $additionalInfoInfo = $("#additionalInfoInfo");

    $nameInfo.hide();
    $titleInfo.hide();
    $funeralDetailsInfo.hide();
    $additionalInfoInfo.hide();

    function handleInput($field, $info, maxLength) {
        $field.on('input', function () {
            if ($field.val().length > maxLength) {
                $info.show();
                $button.prop('disabled', true);
            } else {
                $info.hide();
                $button.prop('disabled', false);
            }
        });
    }

    handleInput($name, $nameInfo, nameMaxLength);
    handleInput($title, $titleInfo, titleMaxLength);
    handleInput($funeralDetails, $funeralDetailsInfo, funeralDetailsMaxLength);
    handleInput($additionalInfo, $additionalInfoInfo, additionalInfoMaxLength);
});