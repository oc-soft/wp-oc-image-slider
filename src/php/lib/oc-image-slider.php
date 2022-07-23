<?php
/*
 * Copyright 2022 oc-soft
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

require_once implode(DIRECTORY_SEPARATOR, [__DIR__, 'shortcode.php']);
require_once implode(DIRECTORY_SEPARATOR, [__DIR__, 'ajax.php']);


/**
 * image slider application
 */
class OcImageSlider {


    /**
     * smart table instance
     */
    static $instance = null;

    /**
     * activate plugin
     */
    function activate() {
    }

    /**
     * deactivate plugin 
     */
    function deactivate() {
    }
     

    /**
     * get inline script
     */
    function get_ajax_inline_script() {
        $ajax_url = admin_url('admin-ajax.php');
        $result = "window.oc = window.oc || { }\n"
            . "window.oc.slider = window.oc.slider || {}\n"
            . "window.oc.slider.ajax = window.oc.slider.ajax || { }\n"
            . "window.oc.slider.ajax.url = '$ajax_url'";
        return $result;
    }

    /**
     * get support size query
     */
    function get_support_size_query() {
        return Config::$instance->get_media_query()['support-query'];
    }


    /**
     * setup style 
     */
    function setup_style($css_dir, $registration) {
        wp_enqueue_style(
            $registration->style);
    }
 
    /**
     * setup script
     */
    function setup_script($js_dir,
        $translations_dir,
        $registration) {

        wp_add_inline_script(
            $registration->script,
            $this->get_ajax_inline_script());

        wp_enqueue_script(
            $registration->script);

        if (!is_admin()) {
            wp_enqueue_script($registration->view_script);
        }
        /*
        wp_set_script_translations(
            self::$script_handle, 
            'oc-image-slider', 
            $translations_dir);
         */
    }

    /**
     * setup wordpress administrator mode script
     */
    function setup_admin_script($js_dir,
        $translations_dir, $registration) {
    }

    /**
     * setup block editor scripts script
     */
    function setup_block_editor_scripts($js_dir,
        $translations_dir,
        $registration) {

        wp_enqueue_script(
            $registration->editor_script);

        wp_enqueue_style(
            $registration->editor_style);

        wp_enqueue_style(
            $registration->style);
    }

    /**
     * render block
     */
    function render_block(
        $block_attributes,
        $block_contents,
        $block) {
        return $block_contents;
    }
    
    /**
     * handle init event
     */
    function on_init(
        $js_dir,
        $css_dir,
        $translations_dir,
        $plugin_dir) {

        $registration = register_block_type($plugin_dir, [
            'render_callback' => [$this, 'render_block']
        ]);


        add_action('wp', function() 
            use($js_dir, $css_dir, $translations_dir, $registration) {
            $this->setup_style($css_dir, $registration);
            $this->setup_script($js_dir, $translations_dir, $registration);
        });

        add_action('admin_init', function() use(
            $js_dir, $translations_dir, $registration) {
            $this->setup_admin_script($js_dir, $translations_dir,
                $registration);
        });
        add_action('enqueue_block_editor_assets', function() 
            use($js_dir, $translations_dir, $registration) {
            $this->setup_block_editor_scripts(
                $js_dir, $translations_dir, $registration);
        });

        add_shortcode('ocslider', 
            function($attr, $contents, $tag) use($plugin_dir) {
                return OcImageSliderShortcode::$instance->handle_shortcode(
                    $attr, $contents, $tag, $plugin_dir);
            });

        OcImageSliderAjax::$instance->register($plugin_dir);
    }




    /**
     * start plugin
     */
    function run(
        $js_dir,
        $css_dir,
        $translations_dir,
        $plugin_dir) {

        add_action('init', function() 
            use($js_dir, $css_dir, $translations_dir, $plugin_dir) {
            $this->on_init($js_dir, $css_dir, $translations_dir, $plugin_dir);
        });
    }
}

OcImageSlider::$instance = new OcImageSlider;

// vi: se ts=4 sw=4 et: