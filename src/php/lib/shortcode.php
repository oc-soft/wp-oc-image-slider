<?php


/**
 *  oc slide shortcode
 */
class OcSlideShortcode {

    /**
     * shortcode instance
     */
    static $instance = null;


    /**
     *  decode html safe contents to plain text 
     */
    function decode_contents($contents) {

        $contents = preg_replace(
            [
                '/<br\s?\/?>/i',
                '/&#8220;/',
                '/&#8221;/',
                '/<p>/i',
                '/<\/p>/i'
            ],
            [
                "\n",
                '"',
                '"',
                '',
                ''
            ], $contents);

        $contents = html_entity_decode($contents);

        return $contents;
    }

    /**
     * handle shortcode
     */
    function handle_shortcode($attr, $contents, $tag, $plugin_dir) {

        
        $contents = $this->decode_contents($contents);
        
        error_log(print_r($contents, true));

        $params = json_decode($contents, true);

        error_log(print_r($params, true));

        $tmpl_contents = '';        
        if (isset($params['img-contents'])) {
            $tmpl_contents = implode('',
                array_map(function($entry) {
                    return "<img src=\"$entry\">";
                }, 
                $params['img-contents']));
        }
        $tmpl_bg = '';
        if (isset($params['backgrounds'])) {
            $tmpl_bg = 
                base64_encode(json_encode($params['backgrounds']));
        }

        $settings = [];

        foreach (['settings', 'control'] as $key) {
            if (isset($params[$key])) {
                $settings[$key] = $params[$key];
            }
        }
        $tmpl_settings = '';
        if (!empty($settings)) {
            $tmpl_settings = base64_encode(json_encode($settings));
        }
 

        $classes = '';
        if (isset($attr['classes'])) {
            $classes = $attr['classes'];
        }

        $style = [];
        if (isset($attr['width'])) {
            $style['width'] = $attr['width'];
        }
        if (isset($attr['height'])) {
            $style['height'] = $attr['height'];
        }

        $style_lines = [];
        foreach ($style as $key => $value) {
            $style_lines[] = "${key}:${value};";
        }
        $style_str = implode('', $style_lines);

        if (strlen($style_str)) {
            $style_str = "style=\"$style_str\"";
        }
        $result = "<div class=\"oc-slide $classes\" $style_str>"
            . "<template class=\"pages\">"
            . "$tmpl_contents"
            . "</template>"
            . "<template class=\"background-b64\">"
            . "<div>"
            . "$tmpl_bg"
            . "</div>"
            . "</template>"
            . "<template class=\"settings-b64\">"
            . "<div>"
            . "$tmpl_settings"
            . "</div>"
            . "</template>"
            . "</div>";

        return $result;
    }
}

OcSlideShortcode::$instance = new OcSlideShortcode;

// vi: se ts=4 sw=4 et:
