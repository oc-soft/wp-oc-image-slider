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


/**
 *  internationalization handler 
 */
class OcImageSliderI18n {

    /**
     *  i18n object 
     */
    static $instance = null;

    /**
     * register translations
     */
    function get_registration_param_for_scripts(
        $translations) {
        $result = [];

        foreach ($translations as $translation_file) {
            if (preg_match(
                '/(.+)-([[:alnum:]_]+)-([[:alnum:]]+)\.json/',
                $translation_file,
                $matches
            )) {
                $result[] = [
                    'handle' => $matches[3],
                    'domain' => $matches[1]
                ];
            }
        }
        return $result;
    }


    /**
     * register translations
     */
    function register_translations(
        $translations_dir,
        $translations) {
        $trans_params = $this->get_registration_param_for_scripts(
            $translations);
        $result = [];
        foreach ($trans_params as $param) {
            wp_register_script($param['handle'],
                false, ['wp-i18n']);

            $state = wp_set_script_translations(
                $param['handle'], $param['domain'], 
                $translations_dir);
            if ($state) {
                $result[] = $param;
            }
        }


        return $result;
    }
    


    /**
     * run i18n  
     */
    function run($translations_dir,
        $translations) {
        $result = $this->register_translations(
            $translations_dir, $translations);
        
        return $result;
    }
}

OcImageSliderI18n::$instance = new OcImageSliderI18n;

// vi: se ts=4 sw=4 et:
