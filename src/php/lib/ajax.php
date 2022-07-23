<?php

/**
 *  ajax handler 
 */
class OcImageSliderAjax {

    /**
     * ajax handler
     */
    static $instance = null;


    /**
     * handle auto pager configuration
     */
    function handle_autopaging_config($plugin_dir) {

        readfile(implode(DIRECTORY_SEPARATOR, 
            [$plugin_dir, 'config', 'autopaging-setting.json']));
        wp_die();
    }


    /**
     * register ajax handler
     */
    function register($plugin_dir) {
        $hdlr = function() use($plugin_dir) {
            $this->handle_autopaging_config($plugin_dir);
        };
        $prefixes = ['wp_ajax', 'wp_ajax_nopriv'];
        foreach ($prefixes as $prefix) {
            add_action($prefix . '_autopaging-config', $hdlr);  
        }
    }
}

OcImageSliderAjax::$instance = new OcImageSliderAjax;

// vi: se ts=4 sw=4 et:
