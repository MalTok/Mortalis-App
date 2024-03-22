$(document).ready(function () {
    let $name = $("#name");
    let $title = $("#title");
    let $funeralDetails = $("#funeralDetails");
    let $additionalInfo = $("#additionalInfo");
    let $pictureFile = $("#pictureFile");


    let $button = $("#necrologyFormButton");

    const nameMaxLength = 77;
    const titleMaxLength = 115;
    const funeralDetailsMaxLength = 450;
    const additionalInfoMaxLength = 250;
    const pictureFileMaxSize = 5242880;

    let $nameInfo = $("#nameInfo");
    let $titleInfo = $("#titleInfo");
    let $funeralDetailsInfo = $("#funeralDetailsInfo");
    let $additionalInfoInfo = $("#additionalInfoInfo");
    let $pictureInfo = $("#pictureInfo");

    $nameInfo.hide();
    $titleInfo.hide();
    $funeralDetailsInfo.hide();
    $additionalInfoInfo.hide();
    $pictureInfo.hide();

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

    function handleFile() {
        $pictureFile.on('input', function () {
            if (this.files[0].size > pictureFileMaxSize) {
                $pictureInfo.show();
                $button.prop('disabled', true);
            } else {
                $pictureInfo.hide();
                $button.prop('disabled', false);
            }
        });
    }

    handleFile();
});