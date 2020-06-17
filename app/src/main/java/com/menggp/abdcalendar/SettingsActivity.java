package com.menggp.abdcalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.menggp.abdcalendar.datamodel.Event;
import com.menggp.abdcalendar.datamodel.EventAlertType;
import com.menggp.abdcalendar.datamodel.EventType;
import com.menggp.abdcalendar.dialogs.SortAndFilterFlushDialogDatable;
import com.menggp.abdcalendar.dialogs.SortAndFilterFlushDialogFragment;
import com.menggp.abdcalendar.repository.DatabaseAdapter;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SettingsActivity extends AppCompatActivity implements SortAndFilterFlushDialogDatable {

    private static final String LOG_TAG = "SettingActivity";

    SharedPreferences generalPrefs;

//    public static final String SHOW_ABOUT_PROGRAM_ACTIVITY = "com.menggp.SHOW_ABOUT_PROGRAM_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // настройка Action bar
        ActionBar actionBar = getSupportActionBar();            // получем доступ к action bar
        actionBar.setTitle(R.string.title_activity_settings);                        // меняем заголовок
        actionBar.setHomeButtonEnabled(true);                   // активируем кнопку "home"
        actionBar.setDisplayHomeAsUpEnabled(true);              // отображаем кнопку "home"

        // Получаем настройки
        generalPrefs = getSharedPreferences(MainActivity.GENERAL_PREFS, MODE_PRIVATE);

        // Получаем элементы с разметки
        RadioGroup defaultAppView = (RadioGroup)findViewById(R.id.setting_default_app_view);
        //RadioButton isCalendarViewRadBtn = (RadioButton)findViewById(R.id.radio_is_calendar_view);

        // значения элементтов по умолчанию
        if ( generalPrefs.getBoolean(MainActivity.DEF_VIEW_IS_CALENDAR, true) ) {
            RadioButton isCalendarViewRadBtn = (RadioButton)findViewById(R.id.radio_is_calendar_view);
            isCalendarViewRadBtn.setChecked(true);
        } else {
            RadioButton isListViewRadBtn = (RadioButton)findViewById(R.id.radio_is_list_view);
            isListViewRadBtn.setChecked(true);
        }

        // Слушатель изменения
        defaultAppView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Создаем редактор prefrences
                SharedPreferences.Editor generalPrefsEdit = generalPrefs.edit();
                switch(checkedId) {
                    case R.id.radio_is_calendar_view:
                        generalPrefsEdit.putBoolean(MainActivity.DEF_VIEW_IS_CALENDAR, true);
                        break;
                    case R.id.radio_is_list_view:
                        generalPrefsEdit.putBoolean(MainActivity.DEF_VIEW_IS_CALENDAR, false);
                        break;
                }
                generalPrefsEdit.apply();
            }
        });

    } // end_method

    /*
     Обработка нажатия меню в Action bar
        - кнопка "home" ( дефолтный ID = android.R.id.home )
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home :
                goMainActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    } // end_method

    /*
        Метод - возвращает на MAIN_ACTIVITY
    */
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    } // end_method

    /*
        Обработка выбора пункта настроек "О программе / About programm"
    */
    public void aboutProgram(View view) {
        Intent intentAboutProgram = new Intent(MainActivity.SHOW_ABOUT_PROGRAM_ACTIVITY);
        startActivity(intentAboutProgram);
    } // end_method

    /*
        Обработка "сброса фильтров и сортировки "
     */
    public void flushSortAndFilters(View view) {
        SortAndFilterFlushDialogFragment dialog = new SortAndFilterFlushDialogFragment();
        dialog.show(getSupportFragmentManager(), "SortAndFilterFlushDialogFragmen");
    }  // end_method

    /*
        Реализация метода интерфейса SortAbdFilterFlushDialogDatable
     */
    @Override
    public void flushSortAndFilter() {
        SharedPreferences sp = getSharedPreferences(MainActivity.SORT_AND_FILTER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        // фильтарция по типу события - умолчания
        spEditor.putBoolean(MainActivity.EV_TYPE_BIRTHDAY_ON, true);
        spEditor.putBoolean(MainActivity.EV_TYPE_ANNIVERSARY_ON, true);
        spEditor.putBoolean(MainActivity.EV_TYPE_MEMODATE_ON, true);
        spEditor.putBoolean(MainActivity.EV_TYPE_HOLIDAY_ON, true);
        spEditor.putBoolean(MainActivity.EV_TYPE_OTHER_ON, true);
        // тип сортировки - по умолчанию
        spEditor.putInt(MainActivity.EV_SORT_TYPE,0);
        // фильтрация по месяцам - по умолчаию
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_01, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_02, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_03, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_04, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_05, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_06, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_07, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_08, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_09, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_10, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_11, true);
        spEditor.putBoolean(MainActivity.EV_MONTH_ON_12, true);

        spEditor.apply();
    } // end_method

    /*
            Метод генерирует услучайные EVENT-ы и записывает их в БД
         */
    public void genRndData(View view) {
        Toast.makeText(getApplicationContext(), "Gen option - checked.", Toast.LENGTH_SHORT).show();
        // Данные для генерации:
        String[] firstNames = new String[]{"Аарон", "Абрам", "Аваз", "Аввакум", "Август", "Августа", "Августин", "Августина", "Авдей", "Авдий", "Авдотья", "Авигея", "Авксентий", "Авраам", "Аврор", "Аврора", "Автандил", "Автоноя", "Агап", "Агапия", "Агата", "Агафон", "Агафья", "Аггей", "Аглая", "Агнес", "Агнесса", "Агнета", "Агния", "Агриппина", "Агунда", "Ада", "Адам", "Аделаида", "Аделина", "Аделия", "Адель", "Адельфина", "Адиля", "Адис", "Адольф", "Адриан", "Адриана", "Адриенна", "Аза", "Азалия", "Азамат", "Азарий", "Азат", "Азиза", "Аида", "Айганым", "Айгерим", "Айгуль", "Айдар", "Айжан", "Айлин", "Айнагуль", "Айнур", "Айрат", "Акакий", "Аким", "Аксён", "Аксинья", "Акулина", "Алан", "Алана", "Алдона", "Алевтин", "Алевтина", "Александр", "Александра", "Александрина", "Алексей", "Алексий", "Ален", "Алёна", "Алеся", "Али", "Алика", "Алико", "Алима", "Алина", "Алира", "Алиса", "Алихан", "Алия", "Алла", "Алмаз", "Алоис", "Алсу", "Алтжин", "Альба", "Альберт", "Альберта", "Альбина", "Альвиан", "Альвина", "Альжбета", "Альфия", "Альфред", "Альфреда", "Аля", "Амадей", "Амадеус", "Амалия", "Амаль", "Аманда", "Амаяк", "Амвросий", "Амелия", "Амин", "Амина", "Амира", "Анаис", "Анаит", "Анастасий", "Анастасия", "Анатолий", "Анвар", "Ангел", "Ангелина", "Андоим", "Андрей", "Андриана", "Андрон", "Андрэ", "Анеля", "Анжей", "Анжела", "Анжелика", "Анжиолетта", "Аникита", "Анисим", "Анисия", "Анисья", "Анита", "Анна", "Антип", "Антон", "Антонин", "Антонина", "Ануфрий", "Анфим", "Анфиса", "Анэля", "Аполлинарий", "Аполлинария", "Аполлония", "Апполинарий", "Арабелла", "Арам", "Ариадна", "Ариана", "Арий", "Арина", "Аристарх", "Аркадий", "Арман", "Армен", "Арно", "Арнольд", "Арон", "Арсен", "Арсений", "Арслан", "Артамон", "Артем", "Артемида", "Артемий", "Артур", "Архелия", "Архип", "Архипп", "Арье", "Арьяна", "Асель", "Асида", "Асия", "Аскольд", "Ассоль", "Астра", "Астрид", "Ася", "Аурелия", "Афанасий", "Афанасия", "Афиноген", "Ахмет", "Ашот", "Аэлита", "Аюна", "Бажена", "Бахрам", "Беата", "Беатриса", "Бежен", "Белинда", "Белла", "Бенедикт", "Бенедикта", "Берек", "Береслава", "Бернар", "Берта", "Биргит", "Бирута", "Богдан", "Богдана", "Боголюб", "Божена", "Болеслав", "Бонифаций", "Бореслав", "Борис", "Борислав", "Борислава", "Боян", "Бриллиант", "Бронислав", "Бронислава", "Бруно", "Булат", "Вадим", "Валентин", "Валентина", "Валерий", "Валерия", "Валерьян", "Вальдемар", "Вальтер", "Ванда", "Ванесса", "Варвара", "Вардан", "Варлаам", "Варлам", "Варфоломей", "Василий", "Василина", "Василиса", "Васса", "Ватслав", "Вацлав", "Велизар", "Велимир", "Велор", "Венди", "Венедикт", "Венера", "Вениамин", "Вера", "Верона", "Вероника", "Версавия", "Веселина", "Весна", "Весняна", "Веста", "Вета", "Ветта", "Вида", "Видана", "Викентий", "Виктор", "Викторина", "Виктория", "Вилен", "Вилена", "Вилли", "Вилора", "Вильгельм", "Винетта", "Виоланта", "Виолетта", "Виргиния", "Виринея", "Виссарион", "Вита", "Виталий", "Виталина", "Витаутас", "Витольд", "Влад", "Влада", "Владимир", "Владислав", "Владислава", "Владлен", "Владлена", "Влас", "Власий", "Властилина", "Володар", "Вольдемар", "Всеволод", "Вячеслав", "Габи", "Габриеле", "Габриэлла", "Гавриил", "Гаврила", "Гай", "Гайдар", "Галактион", "Галина", "Галия", "Гамлет", "Гарри", "Гаспар", "Гастон", "Гаянэ", "Гаяс", "Гевор", "Геворг", "Гелана", "Геласий", "Гелена", "Гелианна", "Гелла", "Гений", "Геннадий", "Генри", "Генриетта", "Генрих", "Георгий", "Георгина", "Гера", "Геральд", "Герасим", "Герда", "Герман", "Гермоген", "Гертруда", "Глафира", "Глеб", "Гликерия", "Глория", "Гоар", "Говхар", "Гордей", "Гордон", "Горислав", "Горица", "Гортензия", "Градимир", "Гражина", "Граф", "Грета", "Григорий", "Гузель", "Гулия", "Гульмира", "Гульназ", "Гульнара", "Гульшат", "Гуннхильда", "Гурий", "Густав", "Гюзель", "Гюльджан", "Давид", "Давлат", "Давыд", "Дайна", "Далия", "Дамиан", "Дамир", "Дамира", "Дан", "Дана", "Даниил", "Данила", "Данислав", "Даниэла", "Дания", "Данна", "Данута", "Даньяр", "Дар", "Дара", "Дарерка", "Дарина", "Дария", "Дарья", "Дарьяна", "Даша", "Даяна", "Дебора", "Дементий", "Демид", "Демократ", "Демьян", "Денис", "Джамал", "Джамиля", "Джанет", "Джеймс", "Джема", "Джемма", "Дженифер", "Дженна", "Дженнифер", "Джереми", "Джессика", "Джоан", "Джозеф", "Джордан", "Джорж", "Джулия", "Джульетта", "Диана", "Дидим", "Дик", "Дилара", "Дильназ", "Дильнара", "Диля", "Димитрий", "Дин", "Дина", "Динар", "Динара", "Динасий", "Динора", "Диодора", "Диомид", "Дионисия", "Дита", "Диша", "Дмитрий", "Добрыня", "Долорес", "Доля", "Доминика", "Домна", "Дональд", "Донат", "Донатос", "Дора", "Дорофей", "Дэнна", "Ева", "Евангелина", "Евгений", "Евгения", "Евграф", "Евдоким", "Евдокия", "Евлампий", "Евлогий", "Евпраксия", "Евсей", "Евстафий", "Евфимия", "Егор", "Екатерина", "Елеазар", "Елена", "Елизавета", "Елизар", "Елисей", "Емельян", "Епифан", "Еремей", "Ермак", "Ермил", "Ермиония", "Ермолай", "Ерофей", "Есения", "Ефим", "Ефимий", "Ефимия", "Ефрем", "Жаклин", "Жан", "Жанетт", "Жанна", "Жасмин", "Ждан", "Женевьева", "Жерар", "Жозефина", "Жорж", "Жюли", "Забава", "Заира", "Закир", "Залина", "Замир", "Замира", "Зара", "Зарема", "Зарина", "Заур", "Захар", "Захария", "Земфира", "Зенон", "ПОКАЗАТЬ ЕЩЕ"};
        String[] lastNames  = new String[]{"imja.name", "Абрамов", "Авдеев", "Агапов", "Агафонов", "Агеев", "Акимов", "Аксенов", "Александров", "Алексеев", "Алехин", "Алешин", "Ананьев", "Андреев", "Андрианов", "Аникин", "Анисимов", "Анохин", "Антипов", "Антонов", "Артамонов", "Артемов", "Архипов", "Астафьев", "Астахов", "Афанасьев", "Бабушкин", "Баженов", "Балашов", "Баранов", "Барсуков", "Басов", "Безруков", "Беликов", "Белкин", "Белов", "Белоусов", "Беляев", "Беляков", "Березин", "Беспалов", "Бессонов", "Бирюков", "Блинов", "Блохин", "Бобров", "Богданов", "Богомолов", "Болдырев", "Большаков", "Бондарев", "Борисов", "Бородин", "Бочаров", "Булатов", "Булгаков", "Буров", "Быков", "Бычков", "в", "Вавилов", "Васильев", "Вдовин", "Верещагин", "Вешняков", "Виноградов", "Винокуров", "Вишневский", "Владимиров", "Власов", "Волков", "Волошин", "Воробьев", "Воронин", "Воронков", "Воронов", "Воронцов", "Высоцкий", "Гаврилов", "Галкин", "Герасимов", "Гладков", "Глебов", "Глухов", "Глушков", "Голиков", "Голованов", "Головин", "Голубев", "Гончаров", "Горбачев", "Горбунов", "Гордеев", "Горелов", "Горлов", "Горохов", "Горшков", "Горюнов", "Горячев", "Грачев", "Греков", "Грибов", "Григорьев", "Гришин", "Громов", "Губанов", "Гуляев", "Гуров", "Гусев", "Гущин", "Давыдов", "Данилов", "Дегтярев", "Дементьев", "Демидов", "Демин", "Демьянов", "Денисов", "Дмитриев", "Добрынин", "Долгов", "допускается.", "Дорофеев", "Дорохов", "Дроздов", "других", "Дружинин", "Дубинин", "Дубов", "Дубровин", "Дьяков", "Дьяконов", "Евдокимов", "Евсеев", "Егоров", "Ежов", "Елизаров", "Елисеев", "Емельянов", "Еремеев", "Еремин", "Ермаков", "Ермилов", "Ермолаев", "Ермолов", "Ерофеев", "Ершов", "Ефимов", "Ефремов", "Жаров", "Жданов", "Жилин", "Жуков", "Журавлев", "Завьялов", "Зайцев", "Захаров", "Зверев", "Зеленин", "Зимин", "Зиновьев", "Злобин", "Золотарев", "Золотов", "Зорин", "Зотов", "Зубков", "Зубов", "Зуев", "Зыков", "Иванов", "Игнатов", "Игнатьев", "Измайлов", "Ильин", "Ильинский", "Исаев", "Исаков", "Казаков", "Казанцев", "Калачев", "Калашников", "Калинин", "Калмыков", "Калугин", "Капустин", "Карасев", "Карпов", "Карташов", "Касаткин", "Касьянов", "Киреев", "Кириллов", "Киселев", "Климов", "Клюев", "Князев", "Ковалев", "Кожевников", "Козин", "Козлов", "Козловский", "Козырев", "Колесников", "Колесов", "Колосов", "Колпаков", "Кольцов", "Комаров", "Комиссаров", "Кондратов", "Кондратьев", "Кондрашов", "Коновалов", "Кононов", "Константинов", "Копылов", "Корнев", "Корнеев", "Корнилов", "Коровин", "Королев", "Коротков", "Корчагин", "Коршунов", "Косарев", "Костин", "Котов", "Кочергин", "Кочетков", "Кочетов", "Кошелев", "Кравцов", "Краснов", "Круглов", "Крылов", "Крюков", "Крючков", "Кудрявцев", "Кудряшов", "Кузин", "Кузнецов", "Кузьмин", "Кукушкин", "Кулагин", "Кулаков", "Кулешов", "Куликов", "Куприянов", "Курочкин", "Лаврентьев", "Лавров", "Лазарев", "Лапин", "Лаптев", "Лапшин", "Ларин", "Ларионов", "Латышев", "Лебедев", "Левин", "Леонов", "Леонтьев", "Литвинов", "Лобанов", "Логинов", "Лопатин", "Лосев", "Лукин", "Лукьянов", "Лыков", "Львов", "Любимов", "Майоров", "Макаров", "Макеев", "Максимов", "Малахов", "Малинин", "Малышев", "Мальцев", "Маркелов", "Маркин", "Марков", "Мартынов", "Масленников", "Маслов", "Матвеев", "материала", "Медведев", "Мельников", "Меркулов", "Мешков", "Мещеряков", "Минаев", "Минин", "Миронов", "Митрофанов", "Михайлов", "Михеев", "Моисеев", "Молчанов", "Моргунов", "Морозов", "Москвин", "Муравьев", "Муратов", "Мухин", "на", "Назаров", "Наумов", "не", "Некрасов", "Нестеров", "Нефедов", "Нечаев", "Никитин", "Никифоров", "Николаев", "Никольский", "Никонов", "Никулин", "Новиков", "Носков", "Носов", "Овсянников", "Овчинников", "Одинцов", "Озеров", "Окулов", "Олейников", "Орехов", "Орлов", "Осипов", "Островский", "Павлов", "Павловский", "Панин", "Панков", "Панкратов", "Панов", "Пантелеев", "Панфилов", "Парамонов", "Парфенов", "Пастухов", "Пахомов", "Петров", "Петровский", "Петухов", "Пименов", "Пирогов", "Платонов", "Плотников", "Поздняков", "Покровский", "Поликарпов", "Поляков", "Пономарев", "Попов", "Постников", "Потапов", "Прокофьев", "Прохоров", "Пугачев", "Раков", "Рогов", "Родин", "Родионов", "Рожков", "Розанов", "Романов", "Рубцов", "Рудаков", "Руднев", "Румянцев", "Русаков", "Русанов", "Рыбаков", "Рыжов", "Рябинин", "Рябов", "Савельев", "Савин", "Савицкий", "Сазонов", "сайтах,", "Сальников", "Самойлов", "Самсонов", "Сафонов", "Сахаров", "Свешников", "Свиридов", "Севастьянов", "Седов", "Селезнев", "Селиванов", "Семенов", "Семин", "Сергеев", "Серебряков", "Серов", "сетях", "Сидоров", "Сизов", "Симонов", "Синицын", "Ситников", "Скворцов", "Смирнов", "Снегирев", "Соболев", "Соколов", "Соловьев", "Сомов", "Сорокин", "Сотников", "Софронов", "социальных", "Спиридонов", "Стариков", "Старостин", "Степанов", "Столяров", "Субботин", "Суворов", "Судаков", "Сурков", "Суслов", "Суханов", "Сухарев", "Сухов", "Сычев", "Тарасов", "Терентьев", "Терехов", "Тимофеев", "Титов", "Тихомиров", "Тихонов", "Ткачев", "Токарев", "Толкачев", "Третьяков", "Трифонов", "Троицкий", "Трофимов", "Трошин", "Туманов", "Уваров", "Ульянов", "Усов", "Успенский", "Устинов", "Уткин", "Ушаков", "Фадеев", "Фамилия", "Федоров", "Федосеев", "Федотов", "Фетисов", "Филатов", "Филимонов", "Филиппов", "Фирсов", "Фокин", "Фомин", "Фомичев", "форумах,", "Фролов", "Харитонов", "Хомяков", "Хромов", "Худяков", "Царев", "Цветков", "Частотность", "Чеботарев", "Черепанов", "Черкасов", "Чернов", "Черный", "Черных", "Чернышев", "Черняев", "Чесноков", "Чижов", "Чистяков", "Чумаков", "Шаповалов", "Шапошников", "Шаров", "Швецов", "Шевелев", "Шевцов", "Шестаков", "Шилов", "Широков", "Ширяев", "Шишкин", "Шмелев", "Шубин", "Шувалов", "Шульгин", "Щеглов", "Щербаков", "Щукин", "этого", "Юдин", "Яковлев", "Яшин"};

        DatabaseAdapter dbAdapter = new DatabaseAdapter(this);



        // Генерируем и добавляем в БД запись
        for (int i=0; i<100; i++) {
            // event_name
            String fullName = firstNames[ (int)((Math.random()*(firstNames.length))-1) ] + " " + lastNames[ (int)((Math.random()*(lastNames.length))-1) ];

            // event_date
            String strDate = "";
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(gc.DAY_OF_YEAR, rndBetween(1, 365));
            int tmpMonth = gc.get(Calendar.MONTH)+1;
            int tmpDay = gc.get(Calendar.DAY_OF_MONTH);
            if (tmpMonth < 10 ) strDate += "0" + tmpMonth;
            else strDate += tmpMonth;
            strDate += "-";
            if (tmpDay < 10 ) strDate += "0" + tmpDay;
            else strDate += tmpDay;

            // event_type
            EventType eventType;
            int eventTypeNum = rndBetween(1,5);
            if (eventTypeNum==1) eventType= EventType.BIRTHDAY;
            else if (eventTypeNum==2) eventType= EventType.ANNIVERSARY;
            else if (eventTypeNum==3) eventType=EventType.MEMODATE;
            else if (eventTypeNum==4) eventType= EventType.HOLIDAY ;
            else eventType=  EventType.OTHER ;

            // event_since_year
            int eventSinceYear = rndBetween(1920, 2020);

            // event_comment
            String eventCommemt = "There's a comment here";

            // event_img
            int eventImg = rndBetween(1, 25);
            if (eventImg==1) eventImg=R.drawable.a01_ev_img_default;
            else if (eventImg==2) eventImg=R.drawable.a02_ev_img_ballon;
            else if (eventImg==3) eventImg=R.drawable.a03_ev_img_bender;
            else if (eventImg==4) eventImg=R.drawable.a04_ev_img_bear_face;
            else if (eventImg==5) eventImg=R.drawable.a05_ev_img_black_cat;
            else if (eventImg==6) eventImg=R.drawable.a06_ev_img_cat_face;
            else if (eventImg==7) eventImg=R.drawable.a07_ev_img_christmas_tree;
            else if (eventImg==8) eventImg=R.drawable.a08_ev_img_dog_face;
            else if (eventImg==9) eventImg=R.drawable.a09_ev_img_elf_face;
            else if (eventImg==10) eventImg=R.drawable.a10_ev_img_finn_the_human;
            else if (eventImg==11) eventImg=R.drawable.a11_ev_img_jack_o_lantern;
            else if (eventImg==12) eventImg=R.drawable.a12_ev_img_monkey_face;
            else if (eventImg==13) eventImg=R.drawable.a13_ev_img_brave_soul;
            else if (eventImg==14) eventImg=R.drawable.a14_ev_img_woomen;
            else if (eventImg==15) eventImg=R.drawable.a15_ev_img_witch;
            else if (eventImg==16) eventImg=R.drawable.a16_ev_img_unicorn;
            else if (eventImg==17) eventImg=R.drawable.a17_ev_img_da_vinchi;
            else if (eventImg==18) eventImg=R.drawable.a18_ev_img_pixel_star;
            else if (eventImg==19) eventImg=R.drawable.a19_ev_img_confetti;
            else if (eventImg==20) eventImg=R.drawable.a20_ev_img_news;
            else if (eventImg==21) eventImg=R.drawable.a21_ev_img_safety_circle;
            else if (eventImg==22) eventImg=R.drawable.a22_ev_img_dali;
            else if (eventImg==23) eventImg=R.drawable.a23_ev_img_card;
            else if (eventImg==24) eventImg=R.drawable.a24_ev_img_ballon2;
            else eventImg=R.drawable.a25_ev_img_fire;

            // event_alert
            EventAlertType eventAlertType;
            int eventAlertTypeNum = rndBetween(1,6);
            if (eventAlertTypeNum==1) eventAlertType= EventAlertType.NO_ALERT;
            else if (eventAlertTypeNum==2) eventAlertType=EventAlertType.SILENT_ALERT;
            else if (eventAlertTypeNum==3) eventAlertType= EventAlertType.IN_DAY_ALERT;
            else if (eventAlertTypeNum==4) eventAlertType= EventAlertType.DAY_BEFORE_ALERT;
            else if (eventAlertTypeNum==5) eventAlertType=EventAlertType.INTENSIVE_ALERT;
            else eventAlertType= EventAlertType.EVERY_MONTH_ALERT;

//            Log.d(LOG_TAG, " === strDate === " + strDate );
//            DateConverter.convertDbNotationToItemNotation(strDate);

            Event event = new Event (i, fullName, strDate, eventType, eventSinceYear, eventCommemt, eventImg, eventAlertType);
            dbAdapter.insertEvent( event );
        }

    } // end_method

    /*
        Метод генерации случайного целого числа в задланном диапазоне
     */
    public static int rndBetween(int start, int end) {
        return start + (int)Math.round( Math.random() * (end-start) );
    } // end_method


} // end_class
