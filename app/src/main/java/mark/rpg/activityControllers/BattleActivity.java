package mark.rpg.activityControllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

import mark.rpg.GameObjects.Enemy;
import mark.rpg.GameObjects.Hero;
import mark.rpg.GameStatus;
import mark.rpg.Inventory;
import mark.rpg.R;
import mark.rpg.Spells.*;


public class BattleActivity extends AppCompatActivity {

    Hero hero;// = new Hero(5,1000,100, 1, 10, 0.05);
    GameStatus gameStatus;

    Enemy enemy;//=new Enemy(3,1200,20,30);


    TextView statisticsLog;
    ImageButton hitButton;
    TextView heroHealth;
    TextView enemyHealth;
    TextView heroMana;
    TextView heroName;
    TextView enemyName;

    ViewGroup skillPanel;
    ImageButton skillPanelOpener;
    ImageButton firstSkill;
    ImageButton secondSkill;
    ImageButton thirdSkill;
    ImageButton fourthSkill;
    ImageButton fifthSkill;

    Spell[] spellArr;
    Spell firstSpell;
    Spell secondSpell;

    TextView mc1;
    TextView mc2;
    TextView mc3;
    TextView mc4;
    TextView mc5;


    ProgressBar heroBar;
    ProgressBar enemyBar;
    ProgressBar heroManaBar;
    ImageView heroPicture;
    ImageView enemyPicture;
   // public static final String

    Boolean skillPanelIsOpen;
    ViewGroup.LayoutParams layotParamsOfPanelOpenerButton;


    Animation heroAttackAnimation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        heroAttackAnimation=AnimationUtils.loadAnimation(this,R.anim.attack_animation);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();


        Intent intent=getIntent();

        String type=intent.getStringExtra("eType");
       // hero=(Hero)getIntent().getParcelableExtra("Hero");

        //TODO передавать индекс сохранения в интентах
        int index=1;
        try {
            gameStatus = new GameStatus().readSave(getApplicationContext(),index);

            hero = gameStatus.getHero();
        }
        catch(Exception e){
            hero=new Hero(5,322,150,10,33,0.1);
            gameStatus=new GameStatus(hero,new Inventory());
            Toast toast = Toast.makeText(getApplicationContext(),
                "Капут (("+e.getClass().toString(),
                Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();}


        Random rnd=new Random();

        // Возможно не соблюдение размероВ
        int ind;

        if(type.equals("w")) {
            ind=rnd.nextInt(Enemy.weakEnemys.length);
            enemy=new Enemy(Enemy.weakEnemys[ind]);
        }
        if (type.equals("n")) {
            ind=rnd.nextInt(Enemy.middleEnemys.length);
            enemy=new Enemy(Enemy.middleEnemys[ind]);
        }
        if(type.equals("s")) {
            ind=rnd.nextInt(Enemy.strongEnemys.length);
            enemy=new Enemy(Enemy.strongEnemys[ind]);
        }


       /*hero.setEmptyListOfSpells();
        Spell[] spellArr=new Spell[5];
       hero.getListOfSpells().toArray(Spell[5] spellArr)
       hero.getListOfSpells().add(new HealingSpell());
        hero.getListOfSpells().add(new HealingSpell());
        hero.getListOfSpells().add(new MeteorSpell());
       hero.getListOfSpells().add(new FireBall());
        hero.getListOfSpells().add(new FireBall());*/



       // FireBall fb=new FireBall();
        //hero.getListOfSpells().add(new MeteorSpell());
        //Spell secondSpell=new MeteorSpell();

        spellArr=new Spell[5];
        spellArr[0]=new FireBall();
        spellArr[1]=new FireBall();
        spellArr[2]=new FireBall();
        spellArr[3]=new HealingSpell();
        spellArr[4]=new FireBall();







         hitButton = (ImageButton) findViewById(R.id.attackButton);


        statisticsLog=(TextView)findViewById(R.id.statistics);
        statisticsLog.setTextSize(12);


        // ПАНЕЛЬ СКИЛЛОВ
        skillPanel=(GridLayout)findViewById(R.id.skillPanel);
        skillPanelOpener=(ImageButton)findViewById(R.id.skilPanelOpener);


        firstSkill=(ImageButton)findViewById(R.id.firstSkill);
        firstSkill.setOnClickListener(onClickListener);
        //firstSkill.setImageResource(hero.getListOfSpells().get(0).getImageId());
        firstSkill.setImageResource(spellArr[0].getImageId());


        secondSkill=(ImageButton)findViewById(R.id.secondSkill);
        secondSkill.setOnClickListener(onClickListener);
        secondSkill.setImageResource(spellArr[1].getImageId());


         thirdSkill=(ImageButton)findViewById(R.id.thirdSkill);
        thirdSkill.setOnClickListener(onClickListener);
        thirdSkill.setImageResource(spellArr[2].getImageId());

         fourthSkill=(ImageButton)findViewById(R.id.fourthSkill);
        fourthSkill.setOnClickListener(onClickListener);
        fourthSkill.setImageResource(spellArr[3].getImageId());

         fifthSkill=(ImageButton)findViewById(R.id.fifthSkill);
        fifthSkill.setOnClickListener(onClickListener);
       fifthSkill.setImageResource(spellArr[4].getImageId());



        mc1=(TextView)findViewById(R.id.mcost1);
        mc2=(TextView)findViewById(R.id.mcost2);
        mc3=(TextView)findViewById(R.id.mcost3);
        mc4=(TextView)findViewById(R.id.mcost4);
        mc5=(TextView)findViewById(R.id.mcost5);

        mc1.setText(Double.toString(spellArr[0].getBaseManacost()));
        mc2.setText(Double.toString(spellArr[1].getBaseManacost()));
        mc3.setText(Double.toString(spellArr[2].getBaseManacost()));
        mc4.setText(Double.toString(spellArr[3].getBaseManacost()));
        mc5.setText(Double.toString(spellArr[4].getBaseManacost()));







        heroName = (TextView) findViewById(R.id.heroName);
        heroName.setText(hero.printNameLevelInfo());

        enemyName = (TextView) findViewById(R.id.enemyName);
        enemyName.setText(enemy.printNameLevelInfo());


        heroHealth = (TextView) findViewById(R.id.heroHealth);
        heroHealth.setTextColor(Color.RED);

        heroMana=(TextView)findViewById(R.id.heroMana);
        heroMana.setTextColor(Color.BLUE);


        enemyHealth = (TextView) findViewById(R.id.enemyHealth);
        enemyHealth.setTextColor(Color.RED);


        heroPicture=(ImageView)findViewById(R.id.heroPicture);
        enemyPicture=(ImageView)findViewById(R.id.enemyPicture);

        heroPicture.setImageResource(hero.getPersonImageId());
        enemyPicture.setImageResource(enemy.getPersonImageId());


        heroBar = (ProgressBar) findViewById(R.id.progressBar);
       // heroBar.setDrawingCacheBackgroundColor(10);
        enemyBar = (ProgressBar) findViewById(R.id.progressBar2);
        //enemyBar.setDrawingCacheBackgroundColor(255);
        heroManaBar=(ProgressBar)findViewById(R.id.manaBar) ;

        heroBar.setMax(hero.getMaxHealth());
        enemyBar.setMax(enemy.getMaxHealth());
        heroManaBar.setMax((int)hero.getMaxMana());


        heroBar.setDrawingCacheBackgroundColor(Color.RED);
        enemyBar.setDrawingCacheBackgroundColor(Color.RED);
        heroManaBar.setDrawingCacheBackgroundColor(Color.BLUE);




        statisticsLog.setMovementMethod(new ScrollingMovementMethod());

        skillPanel.setVisibility(Button.INVISIBLE);
        skillPanelIsOpen = false;
        layotParamsOfPanelOpenerButton=skillPanelOpener.getLayoutParams();

      //  enemyLogTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

    }

    @Override
    protected void onResume() {
        super.onResume();
        redraw();
        //ImageButton firstSkill=(ImageButton)findViewById(R.id.firstSkill);
        //firstSkill.
    }

    private void redraw() {

        heroHealth.setText(Integer.toString(hero.getHealth()));
        enemyHealth.setText(Integer.toString(enemy.getHealth()));
        heroMana.setText(Integer.toString((int)hero.getMana()));

        heroBar.setProgress(hero.getHealth());

        enemyBar.setProgress(enemy.getHealth());

        heroManaBar.setProgress((int)hero.getMana());

    }

    public void onClick(View view) {
        redraw();
        hero.hitWithLog(enemy,statisticsLog);
        heroPicture.startAnimation(heroAttackAnimation);
        checkWinner();
        redraw();
        enemysTurn();


        //statisticsLog
    }
    public void SkillPanelOpen_OnClick(View view){
        if (!skillPanelIsOpen) {
            hitButton.setVisibility(Button.INVISIBLE);
            skillPanelOpener.setLayoutParams(hitButton.getLayoutParams());
            skillPanel.setVisibility(Button.VISIBLE);
        }
        else{
            hitButton.setVisibility(Button.VISIBLE);
            skillPanelOpener.setLayoutParams(layotParamsOfPanelOpenerButton);
            skillPanel.setVisibility(Button.INVISIBLE);

        }
        skillPanelIsOpen = !skillPanelIsOpen;
    }

    // TODO сделать листенеров и список спеллов
   /* public void onClickFirstSkill(View view) {

        HealingSpell hs=new HealingSpell();

        hs.effectWithLog(hero,enemy,statisticsLog);
        redraw();
        checkWinner();
        enemysTurn();
        //Вывод знач хила
    }

    public void onClickSecondSkill(View view) {
        FireBall fb=new FireBall();
        fb.effectWithLog(hero,enemy,statisticsLog);
        redraw();
        checkWinner();
    }
    */

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.firstSkill:
                   // hero.getListOfSpells().get(0).effectWithLog(hero,enemy,statisticsLog);
                    FireBall fb=new FireBall();
                    spellArr[0].effectWithLog(hero,enemy,statisticsLog);

                    enemysTurn();
                    break;
                case R.id.secondSkill:
                    spellArr[1].effectWithLog(hero,enemy,statisticsLog);
                    enemysTurn();
                    break;
                case R.id.thirdSkill:
                    spellArr[2].effectWithLog(hero,enemy,statisticsLog);
                    enemysTurn();
                    break;
                case R.id.fourthSkill:
                    spellArr[3].effectWithLog(hero,enemy,statisticsLog);
                    enemysTurn();
                    break;
                case R.id.fifthSkill:
                    spellArr[4].effectWithLog(hero,enemy,statisticsLog);
                    enemysTurn();
                    break;

            }
        }
    };


    private void enemysTurn() {
        enemy.hitWithLog(hero, statisticsLog);
        //enemy.hit(hero);
        redraw();

        checkWinner();
        hero.manaRegen();
    }

    private void checkWinner(){
        //  WIN
        if (hero.isAlive() && enemy.isAlive()!=true){
            hitButton.setVisibility(Button.INVISIBLE);
            statisticsLog.append("\n");;
            statisticsLog.setTextColor(Color.RED);
            statisticsLog.append("ПОБЕДА");


            enemy.getReward(gameStatus.getInventory());

            try {
                gameStatus.writeSave(getApplicationContext(), 1);
            }
            catch(Exception e){Toast toast = Toast.makeText(getApplicationContext(),
                    "Капут (("+e.getClass().toString(),
                    Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();}


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Победа!")
                    .setMessage("Добыто ??? золота")
                    .setCancelable(false)
                    .setNeutralButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(getApplicationContext(),ChooseEnemy.class));
                                    dialog.cancel();


                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
            /*Toast toast = Toast.makeText(getApplicationContext(),
                    "Ты Подебил!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            */
        }
        // LOSE
        if (hero.isAlive()!=true && enemy.isAlive()){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Поражение!")
                    .setMessage("Ну как ты мог((")
                    .setCancelable(false)
                    .setNeutralButton("ОК(",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();


            Toast toast = Toast.makeText(getApplicationContext(),
                    "Капут ((",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            redraw();


            hitButton.setVisibility(Button.INVISIBLE);
            skillPanel.setEnabled(false);
            return;
        }
        if (hero.isAlive() == false) {

            hitButton.setVisibility(Button.INVISIBLE);
            //скилл панель убрать

        }
    }

    }





