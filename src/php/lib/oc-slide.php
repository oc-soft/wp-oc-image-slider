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
 * slide application
 */
class OcSlide {


    /**
     * smart table instance
     */
    static $instance = null;

    /**
     * classes parent of table
     */
    static $table_container_classes = [
        'oc-slide-table'
    ];
    
    /**
     * classes for html element
     */
    static $table_classes = [];
    

    /**
     * container tag for dl which replaces table row.
     */
    static $container_tag = 'ul';


    /**
     * container element tag for dl which replace table row.
     */
    static $container_element_tag = 'li';

    /**
     * container classes for container tag
     */
    static $container_classes = ['oc-smart-table-container'];

    /**
     * script handle
     */
    static $script_handle = 'oc-slide';

    /**
     * script handle for editor
     */
    static $script_editor_handle = 'oc-slide-editor';

    /**
     * javascript name
     */
    static $js_script_name = 'oc-slide.js';

    /**
     *  javascript editor name 
     */
    static $js_script_editor_name ='oc-slide-editor.js';

    /**
     * css style sheet name
     */
    static $css_style_name = 'oc-slide.css';

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
            . "window.oc.slide = window.oc.slide || {}\n"
            . "window.oc.slide.ajax = window.oc.slide.ajax || { }\n"
            . "window.oc.slide.ajax.url = '$ajax_url'";
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
    function setup_style($css_dir) {
        wp_register_style(self::$script_handle,
           implode('/', [$css_dir, self::$css_style_name])); 
        wp_enqueue_style(self::$script_handle);
    }
 
    /**
     * setup script
     */
    function setup_script($js_dir,
        $translations_dir) {


        wp_register_script(self::$script_handle,
            implode('/', [$js_dir, self::$js_script_name]),
            [], false);


        wp_add_inline_script(
            self::$script_handle,
            $this->get_ajax_inline_script());

        wp_enqueue_script(self::$script_handle);

        wp_set_script_translations(
            self::$script_handle, 
            'oc-slide', 
            $translations_dir);

    }

    /**
     * setup wordpress administrator mode script
     */
    function setup_admin_script($js_dir,
        $translations_dir) {
        $deps = ['wp-block-library'];
        
        wp_register_script(self::$script_editor_handle,
            implode('/', [$js_dir, self::$js_script_editor_name]),
            $deps, 
            false, true);

 
    }

    /**
     * setup block editor scripts script
     */
    function setup_block_editor_scripts($js_dir,
        $translations_dir) {

        wp_enqueue_script(self::$script_editor_handle);

        wp_set_script_translations(
            self::$script_editor_handle,
            'oc-slide', 
            $translations_dir);
    }



    
    /**
     * handle init event
     */
    function on_init(
        $js_dir,
        $css_dir,
        $translations_dir,
        $plugin_dir) {

        add_action('wp', function() use($js_dir, $css_dir, $translations_dir) {
            $this->setup_style($css_dir);
            $this->setup_script($js_dir, $translations_dir);

        });

        add_action('admin_init', function() use($js_dir, $translations_dir) {
            $this->setup_admin_script($js_dir, $translations_dir);
        });
        add_action('enqueue_block_editor_assets', function() 
            use($js_dir, $translations_dir) {
            $this->setup_block_editor_scripts($js_dir, $translations_dir);
        });

        add_shortcode('ocslide', 
            function($attr, $contents, $tag) use($plugin_dir) {
                return OcSlideShortcode::$instance->handle_shortcode(
                    $attr, $contents, $tag, $plugin_dir);
            });
        OcSlideAjax::$instance->register($plugin_dir);
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

OcSlide::$instance = new OcSlide;

// vi: se ts=4 sw=4 et:
