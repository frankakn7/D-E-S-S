<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Exam;
use Storage;

class ExamController extends Controller
{
    public $jsonDump;

    public function __construct() {
        /** Wird hier geladen, da eloquent Modells die in laravel
           verwendet werden wohl erst einmal auf einer SQL Datenbank
           basieren und die Daten ja nur einmal von der JSON-Datei
           reingeladen werden muessen. */
        $this->jsonDump = Storage::disk('local')->get('vorlesungszeiten.json');
    }

    public function checkExamDate(Request $request) {
        $data = $request->all();
        \Log::info(json_encode($data));

        $semesters = json_decode($this->jsonDump, true);
        $date = date('Y-m-d', strtotime($data['exam-date']));
        $name = $data['exam-name'];
        $checkWeekends = array_key_exists("exam-check-weekends", $data);
        $res = [
            'examName' => $name,
        ];

        foreach ($semesters as $semester) {
            $startDate = date("Y-m-d", strtotime($semester['start']));
            $endDate = date("Y-m-d", strtotime($semester['end']));

            \Log::info("Test if ${startDate} <= ${date} <= ${endDate}");
            if (($date >= $startDate) && ($date <= $endDate)) {
                $res['inSemester'] = true;
                $res['semesterName'] = $semester['semester'];
                break;
            }
        }


        \Log::info("Check if ${checkWeekends}");
        if ($checkWeekends) {
            $day = date("D", strtotime($date));
            \Log::info($day);
            if ($day == "Sat" || $day == "Sun") {
                $res['onWeekend'] = true;
            }
        }

        return view('welcome', ['result' => $res]);
    }
}
